# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Overview

Build Bruh is a Spring Boot 4.1 (Java 17+) REST API that tracks CI builds, build numbers, build-set templates ("environments"), servers and deployments. It is backed by MariaDB with Flyway migrations. Tests are written in Groovy with Spock.

**The API is contract first.** The OpenAPI contract `src/main/resources/openapi/build-bro-api.yaml` is the source of the API surface. `openapi-generator` (execution `api-from-contract` in `pom.xml`) generates the endpoint interfaces and the API model records from it into `target/generated-sources/openapi` during `generate-sources`. To change the API, edit the contract first, then implement what the regenerated code demands. The UI (`../build-bro-ui`) depends on the JSON, so property names, types and order must stay stable unless the change is meant to break clients.

## Commands

```bash
./mvnw verify                        # compile + run all tests + JaCoCo report (target/site/jacoco)
./mvnw test                          # tests only
./mvnw test -Dtest='BuildEndpointImplSpec'          # single spec
./mvnw test -Dtest='BuildEndpointImplSpec#Search'   # single feature method
./mvnw spring-boot:run               # run the API on :8080
./mvnw verify sonar:sonar            # what CI runs on GitHub Actions
./mvnw generate-sources              # regenerate interfaces and models after editing the contract
./redoc-docs.sh                      # Redocly rendering of the contract to target/openapi/redoc/index.html (needs Node)
SERVER_PORT=18080 ./mvnw verify      # when something else holds :8080 (the REST specs use the real port)
```

Maven needs **JDK 17 or newer** (`JAVA_HOME`). CI builds with JDK 21. Spock specs (Groovy 5, `spock-*-groovy-5.0`) in `src/test/groovy` are compiled by `gmavenplus-plugin`, and Surefire is configured to pick up both `*Spec` and `*Test` classes.

**Tests start their own MariaDB with Testcontainers, so they need a running Docker daemon** and nothing else. `TestDatabase` (in `src/test/groovy`) holds one static `MariaDBContainer`, which both base specs pull in with `@ImportTestcontainers`. `@ServiceConnection` points the datasource at it, overriding `spring.datasource.*`. The image is set by the `test.mariadb.image` Maven property (default `mariadb:10.10`), which you can override with `-Dtest.mariadb.image=mariadb:11.4`.

**The app itself (`spring-boot:run`, the jar) still needs a real MariaDB.** `application.properties` expects `jdbc:mariadb://localhost:3306/build_bruh` with user/password/database all set to `build_bruh`. Start one with:

```bash
docker run --name build_bruh_db -d -p 3306:3306 -e MYSQL_DATABASE=build_bruh -e MYSQL_USER=build_bruh \
  -e MYSQL_PASSWORD=build_bruh -e MYSQL_ROOT_PASSWORD=build_bruh mariadb:10.10
```

(`setup-db.sh` does roughly the same but uses an inconsistent container name and a `build_bruh` docker network. Create that network first or use the command above.)

Docker image: `./mvnw package`, then build the `Dockerfile`, which copies `target/build-bro-api-0.2.0.jar`, so update the Dockerfile when `<version>` changes in `pom.xml`. At runtime it takes `DB_URL`, `DB_USR` and `DB_PWD` env vars.

## Architecture

Package root: `com.egalaber.buildbro`. Requests move through these layers:

1. **`api.v1`**: endpoint *interfaces*, **generated** from the contract, one per tag (`BuildsApi`, `BuildSetsApi`, `DeploymentsApi`, …). They carry the Spring MVC mappings, and their method names are the contract's `operationId`s. **`api.v1.impl`**: hand-written `@RestController` classes implement them and contain no mapping annotations. A new route starts in the contract; after regenerating, the compiler points at the implementation to add.
2. **`api.model`**: API DTOs, **generated** from the contract's schemas as Java records, all prefixed `I` (`IBuild`, `IBuildSearch`, …). The prefix does not mean they are interfaces. Never edit or add them by hand, and never put behaviour in them: logic belongs in `core` (e.g. `core.service.Paging` builds the `PageRequest` of a search, `BuildService.searchFor` the search of a build template). Generated constructors apply the contract's `default`s (paging defaults, zero counters) and turn `null` collections into empty, **mutable** ones; there are no defensive copies, so mappers must hand over copies, never Hibernate's persistent collections (see `BuildTemplateMapper`). **Constructor parameter order is the contract's property order**: mappers call the constructors positionally, so reordering properties in the contract can silently swap arguments of the same type. Enums (`SortDirection`, `IExceptionInfo.KeyEnum`) accept any case on input (`useEnumCaseInsensitive`), because the UI sends e.g. `sortDirection: 'desc'`. Date-times are `Instant` (`typeMappings` `DateTime=Instant`), serialized in UTC with a `Z` (`2026-11-22T19:42:48Z`). Entities use `Instant` too, and `datetime` columns hold UTC (`hibernate.jdbc.time_zone=UTC`); never store `LocalDateTime.now()` (issue #15).
3. **`core.service`**: business logic. Services take and return API models (or `Page<Entity>` for searches) and convert using static mappers in **`core.mapping`** (`XMapper.toApi(...)`).
4. **`core.repository`**: Spring Data JPA repositories plus `*Specs` classes of static `Specification` factories. Queries are built by composing specs (see `BuildService.toSpecification`), not with derived query methods or JPQL. Specs use the Hibernate JPA metamodel (`Build_`, generated by `hibernate-processor` at compile time, configured as an annotation processor in `pom.xml`), so a clean compile is needed after entity changes.
5. **`core.domain`**: JPA entities. The schema is owned by Flyway (`src/main/resources/db/migration`) and Hibernate runs with `ddl-auto=validate`. **Every entity change needs a new `V*__*.sql` migration.** Entities use class-level Lombok `@Getter`/`@Setter` only. Keep `equals`/`hashCode`/`toString` hand-written and never use `@Data` or `@EqualsAndHashCode` on entities: those would pull in ids and lazy collections.

Cross-cutting pieces:
- **Errors**: throw subclasses of `api.fault.BuildBroException` (`DataNotFoundException`, `InvalidRequestException`). They are unchecked (generated interfaces declare no `throws`) and carry an HTTP status. `BuildBroExceptionHandler` turns them into `IExceptionInfo` JSON responses. Document each 404/400 as a response in the contract (`$ref: '#/components/responses/NotFound'` / `InvalidRequest`).
- **Events**: `BuildEndpointImpl.createBuild` publishes `BuildCreatedEvent`. `BuildCreatedEventListener` (`@Async`, enabled via `@EnableAsync`) calls `TrackingService`, which auto-creates the `Project`/`Branch` rows for the build. Projects and branches therefore come into existence as a side effect of build creation.
- **Label definitions** (issue #7): optional, per label key (`LabelDefinitionService`). Every service method that writes labels (builds, deployments, labels added to a deployment's builds, build-set templates) must call `LabelValidator.check(...)` before writing; it only rejects when the `label.validation` setting is `STRICT`. `LabelValues` holds the lenient, case-insensitive value checks, which `LabelViolationService` also uses for the conformance report.
- **Logging**: `core.aop.LoggingAspect` (registered via `@Import` in `BuildBroApiApplication`) logs every method result and exception in `core.service.*` and `api.v1.impl.*`.
- **API docs**: the contract is the documentation. `api.ContractDocsConfig` serves it at `/openapi/build-bro-api.yaml`, and `static/swagger-ui/index.html` shows it in Swagger UI (`/swagger-ui/index.html`, from the `org.webjars:swagger-ui` webjar). There is no springdoc and no spec generated from the code (`/v3/api-docs` does not exist). Static docs: `./redoc-docs.sh` renders the contract with Redocly CLI (pinned version, via `npx`, so it needs Node; the Maven build does not) to `target/openapi/redoc/index.html`, and `package` copies the contract to `target/openapi/`. CI runs the script after `./mvnw verify` and uploads `target/openapi/` as the `api-docs` artifact. Contract descriptions are Markdown (Redoc and Swagger UI render it). Quote enum values such as `'OFF'`, `'ON'`, `'YES'`, `'NO'` in the contract: YAML 1.1 reads them bare as booleans.

## Tests

- `BaseBuildBroSpec`: `@SpringBootTest` + `@Transactional` service-level specs.
- Both base specs import the shared Testcontainers MariaDB (`@ImportTestcontainers(TestDatabase)`). New Spring test base classes need the same annotation.
- `BaseRestSpec`: boots the server on the real port (`DEFINED_PORT`, 8080, so it will clash with a locally running app; use `SERVER_PORT=18080 ./mvnw verify`) and calls `/api/v1` through `TestRestTemplate`. In Boot 4 that class lives in `org.springframework.boot.resttestclient` and needs the `spring-boot-resttestclient` and `spring-boot-restclient` test dependencies. It is not transactional.
- Both run `@Sql('/testdata.sql')` before each feature. That script deletes all tables and reseeds fixtures, so assertions rely on its data (e.g. project `backend`, branch `main`). When adding tables, add them to the delete list in FK-safe order. Write comments in it as `-- …`, never `# …`: Spring's script runner joins each statement onto one line, so a `#` comment silently swallows the statement after it.
- Specs build API models with the named-argument factories in `ApiModels` (`aBuild(project: 'backend', buildNumber: 1)`, `aServer(...)`, …), via explicit `import static com.egalaber.buildbro.api.model.ApiModels.aBuild` etc. Use explicit imports, not a wildcard: Sonar can't see dynamic Groovy calls and flags a wildcard import as unused, and removing it breaks the specs at runtime, not at compile time. Unknown keys throw. Records can't be changed, so to "modify" one, build a new one.
- `EqualsAndHashCode*Test` use EqualsVerifier on the domain entities and API models.
- `ContractSpec` checks that the app implements exactly the contract's operations (a generated interface without a `@RestController` fails it) and that the contract and Swagger UI are served.
