# Build Bruh

[![Build 'n Sonar](https://github.com/esistegalaber/build-bro-api/actions/workflows/build.yml/badge.svg?branch=main)](https://github.com/esistegalaber/build-bro-api/actions/workflows/build.yml)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=esistegalaber_build-bro-api&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=esistegalaber_build-bro-api)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=esistegalaber_build-bro-api&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=esistegalaber_build-bro-api)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=esistegalaber_build-bro-api&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=esistegalaber_build-bro-api)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=esistegalaber_build-bro-api&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=esistegalaber_build-bro-api)

[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=esistegalaber_build-bro-api&metric=coverage)](https://sonarcloud.io/summary/new_code?id=esistegalaber_build-bro-api)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=esistegalaber_build-bro-api&metric=bugs)](https://sonarcloud.io/summary/new_code?id=esistegalaber_build-bro-api)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=esistegalaber_build-bro-api&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=esistegalaber_build-bro-api)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=esistegalaber_build-bro-api&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=esistegalaber_build-bro-api)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=esistegalaber_build-bro-api&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=esistegalaber_build-bro-api)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=esistegalaber_build-bro-api&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=esistegalaber_build-bro-api)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=esistegalaber_build-bro-api&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=esistegalaber_build-bro-api)

Build Bruh is a small REST service that keeps track of **what your CI has built and where it has been deployed**.

When many projects and branches are built in different pipelines, simple questions get hard to answer:
*What is the latest build of `backend` on `main`? Which builds make up our staging environment? What is running on
server X right now?* Build Bruh answers them by acting as a central record that every pipeline talks to.

## What it does

- **Hands out build numbers.** Each project/branch pair has its own counter. A pipeline asks for the next number
  instead of relying on the CI server's job number, so numbering survives job re-creation or switching CI systems.
- **Records builds.** After a successful build, the pipeline registers it (project, branch, build number) and can
  attach free-form **labels** (key/value pairs such as `git-sha`, `docker-image` or `qa=passed`). Labels can be added
  later, e.g. once a build has passed further testing.
- **Optionally describes labels.** A *label definition* gives a label key a type (boolean, integer, URL, enum,
  regular expression), the values it allows and a display style (info, success, warning, error). Definitions can
  be added at any time: a conformance report shows which stored labels don't fit, and switching label validation to
  `STRICT` then rejects labels that don't fit. Keys without a definition stay free-form.
- **Keeps track of projects and branches** automatically. They are created the first time a build for them arrives,
  and can be marked active or inactive to hide old branches.
- **Describes environments as build sets.** A *build-set template* is a named list of entries like
  "project `backend`, branch `main`, labelled `qa=passed`", optionally pinned to a specific build number. Resolving
  the template returns the concrete builds that currently match, i.e. *the exact set of artifacts to deploy*.
- **Records deployments.** A deployment ties a set of builds to a **server** at a point in time, so you can ask
  what is currently deployed where and search the deployment history.
- **Search and stats** over builds and deployments by project, branch, label and server, plus simple counts for
  dashboards.

## A typical pipeline flow

```shell
API=http://localhost:8080/api/v1

# 1. Get the next build number for this project/branch
curl -s -X POST $API/build-numbers/next -H 'Content-Type: application/json' \
     -d '{"project":"backend","branch":"main"}'
# -> {"project":"backend","branch":"main","number":42}

# 2. Build, then register the build with some labels
curl -s -X POST $API/builds/create -H 'Content-Type: application/json' \
     -d '{"project":"backend","branch":"main","buildNumber":42,
          "labels":[{"key":"git-sha","value":"3f2c1ab"}]}'

# 3. Later: resolve an environment template into the concrete builds to deploy
curl -s $API/build-sets/of/staging

# 4. After deploying, record what went where
curl -s -X POST $API/deployments -H 'Content-Type: application/json' \
     -d '{"serverName":"staging-01","builds":[{"id":123}]}'

# 5. What is running on that server now?
curl -s $API/deployments/staging-01
```

## API overview

All endpoints live under `/api/v1`. The API is defined contract first: the OpenAPI contract
[`src/main/resources/openapi/build-bro-api.yaml`](src/main/resources/openapi/build-bro-api.yaml) is the source of
the API surface, and the build generates the endpoint interfaces and API models from it. The running app serves the
contract at `/openapi/build-bro-api.yaml` and shows it in Swagger UI at
<http://localhost:8080/swagger-ui/index.html>.

For static docs, `./redoc-docs.sh` (needs Node) renders the contract with [Redocly](https://redocly.com/redoc-ce)
into a self-contained page at `target/openapi/redoc/index.html`; `./mvnw package` copies the contract next to it.
CI publishes both as the `api-docs` build artifact.

| Area          | Endpoints                                                                                              |
|---------------|--------------------------------------------------------------------------------------------------------|
| Build numbers | `POST /build-numbers/next`, `GET /build-numbers/current/{project}/{branch}`, `POST /build-numbers/set`  |
| Builds        | `POST /builds/create`, `GET /builds/{id}`, `POST /builds/search`, `POST /builds/add-labels/{id}`        |
| Build sets    | `GET/POST /build-sets`, `GET/DELETE /build-sets/{name}`, `GET /build-sets/of/{name}`, `POST /build-sets/verify` |
| Deployments   | `POST /deployments`, `GET /deployments/{serverName}`, `POST /deployments/search`, `POST /deployments/add-labels-to-builds/{id}` |
| Servers       | `GET /servers`, `POST /servers`                                                                        |
| Projects      | `GET /projects`, `POST /projects/{active,inactive}/{projectId}`, `POST /projects/{branch-active,branch-inactive}/{branchId}` |
| Labels        | `GET /label-definitions`, `GET/PUT/DELETE /label-definitions/{key}`, `GET /label-violations`, `GET/PUT /label-settings` |
| Misc          | `GET /search-data` (known projects, branches, label keys, label definitions), `GET /stats`             |

## Tech stack

Java 25, Spring Boot 4, Spring Data JPA / Hibernate, MariaDB with Flyway migrations, OpenAPI contract first (openapi-generator, Swagger UI).
Tests are Spock specifications that run against a MariaDB started by Testcontainers.

## Build

Requires JDK 25 or newer, and a running Docker daemon for the tests.

```shell
./mvnw verify
```

## Run locally

The application needs a MariaDB database:

```shell
docker run --name build_bruh_db -d -p 3306:3306 -e MYSQL_DATABASE=build_bruh -e MYSQL_USER=build_bruh \
  -e MYSQL_PASSWORD=build_bruh -e MYSQL_ROOT_PASSWORD=build_bruh mariadb:10.10

./mvnw spring-boot:run
```

The schema is created and migrated by Flyway on startup. The API listens on port 8080.

## Health checks

For orchestrators, load balancers and people, without credentials, on the API's port:

| Check     | URL                          | `200` means                                    | Not `200` (`503`)                                       |
|-----------|------------------------------|------------------------------------------------|---------------------------------------------------------|
| Liveness  | `/actuator/health/liveness`  | The process works. Ignores the database.       | Broken; restarting it may help.                          |
| Readiness | `/actuator/health/readiness` | Ready to serve: started, and the database answers. | Don't send traffic: the database is unreachable, or it is shutting down. |

```shell
curl -s http://localhost:8080/actuator/health/readiness
# -> {"status":"UP"}
```

`/actuator/health` combines both. The responses show only the status, no details. While the API shuts down, it refuses new
connections, so the checks fail with connection refused instead of `503`; requests in progress still finish.

## Run as Docker service

Published images are on GHCR, public, no login needed:

```shell
docker pull ghcr.io/esistegalaber/build-bro-api:latest   # the newest release (also X.Y.Z and X.Y)
docker pull ghcr.io/esistegalaber/build-bro-api:main     # the newest commit on main (also sha-<7>)
```

PRs publish `pr-<N>` (the PR number), deleted when the PR closes. Only images whose CI passed (tests, Trivy scan,
smoke test) are published. The images are amd64 only.

To build it yourself: the image builds from a clean checkout; it compiles the API itself, so no `./mvnw package`
is needed first.

```shell
docker build -t build-bro-api .
docker run -d --read-only --tmpfs /tmp -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:mariadb://<db-host>:3306/build_bruh \
  -e SPRING_DATASOURCE_USERNAME=build_bruh \
  -e SPRING_DATASOURCE_PASSWORD=build_bruh \
  build-bro-api
```

The image contains no database settings or credentials: pass them with the `SPRING_DATASOURCE_*` variables. It runs
as a non-root user (`65532`) on a distroless Java 25 base without a shell, and works with a read-only root filesystem
when `/tmp` is writable (`--tmpfs /tmp`). JVM memory follows the container's limit (`-XX:MaxRAMPercentage=75`);
override it with `-e JAVA_TOOL_OPTIONS=...`.

## Releases

Create a GitHub release with a new tag `vX.Y.Z` on a commit of `main`. CI builds, tests and checks that commit and
publishes the image as `X.Y.Z`, `X.Y` and `latest` (pre-releases such as `v1.0.0-rc1` don't get `latest`). The
version comes from the tag only: `pom.xml` stays at `1.0-SNAPSHOT` and is never bumped.

## License

See [LICENSE](LICENSE).
