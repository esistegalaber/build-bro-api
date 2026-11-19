# Build Bro

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=esistegalaber_build-bro-api&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=esistegalaber_build-bro-api)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=esistegalaber_build-bro-api&metric=coverage)](https://sonarcloud.io/summary/new_code?id=esistegalaber_build-bro-api)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=esistegalaber_build-bro-api&metric=bugs)](https://sonarcloud.io/summary/new_code?id=esistegalaber_build-bro-api)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=esistegalaber_build-bro-api&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=esistegalaber_build-bro-api)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=esistegalaber_build-bro-api&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=esistegalaber_build-bro-api)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=esistegalaber_build-bro-api&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=esistegalaber_build-bro-api)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=esistegalaber_build-bro-api&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=esistegalaber_build-bro-api)

Build Bro is a small REST service that keeps track of **what your CI has built and where it has been deployed**.

When many projects and branches are built in different pipelines, simple questions get hard to answer:
*What is the latest build of `backend` on `main`? Which builds make up our staging environment? What is running on
server X right now?* Build Bro answers them by acting as a central record that every pipeline talks to.

## What it does

- **Hands out build numbers.** Each project/branch pair has its own counter. A pipeline asks for the next number
  instead of relying on the CI server's job number, so numbering survives job re-creation or switching CI systems.
- **Records builds.** After a successful build, the pipeline registers it (project, branch, build number) and can
  attach free-form **labels** (key/value pairs such as `git-sha`, `docker-image` or `qa=passed`). Labels can be added
  later, e.g. once a build has passed further testing.
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

All endpoints live under `/api/v1`. The full, interactive documentation is served by Swagger UI at
<http://localhost:8080/swagger-ui/index.html> (OpenAPI spec at `/v3/api-docs`).

`./mvnw verify` also writes static docs to `target/openapi/`: the spec as `openapi.json`/`openapi.yaml` and a
self-contained HTML page at `html/index.html`. CI publishes the same files as the `api-docs` build artifact.

| Area          | Endpoints                                                                                              |
|---------------|--------------------------------------------------------------------------------------------------------|
| Build numbers | `POST /build-numbers/next`, `GET /build-numbers/current/{project}/{branch}`, `POST /build-numbers/set`  |
| Builds        | `POST /builds/create`, `GET /builds/{id}`, `POST /builds/search`, `POST /builds/add-labels/{id}`        |
| Build sets    | `GET/POST /build-sets`, `GET/DELETE /build-sets/{name}`, `GET /build-sets/of/{name}`, `POST /build-sets/verify` |
| Deployments   | `POST /deployments`, `GET /deployments/{serverName}`, `POST /deployments/search`, `POST /deployments/add-labels-to-builds/{id}` |
| Servers       | `GET /servers`, `POST /servers`                                                                        |
| Projects      | `GET /projects`, `POST /projects/{active,inactive}/{projectId}`, `POST /projects/{branch-active,branch-inactive}/{branchId}` |
| Misc          | `GET /search-data` (known projects, branches, label keys), `GET /stats`                                |

## Tech stack

Java 17+, Spring Boot 4, Spring Data JPA / Hibernate, MariaDB with Flyway migrations, springdoc-openapi.
Tests are Spock specifications that run against a MariaDB started by Testcontainers.

## Build

Requires JDK 17 or newer, and a running Docker daemon for the tests.

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

## Run as Docker service

```shell
./mvnw package -DskipTests
docker build -t build-bro-api .
docker run -d -p 8080:8080 \
  -e DB_URL=jdbc:mariadb://<db-host>:3306/build_bruh -e DB_USR=build_bruh -e DB_PWD=build_bruh \
  build-bro-api
```

## License

See [LICENSE](LICENSE).
