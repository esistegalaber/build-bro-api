# syntax=docker/dockerfile:1

# Build stage: compiles the API from source (the endpoint interfaces are generated from the OpenAPI contract),
# so the image needs no prior ./mvnw package on the host. Temurin 25, like CI's setup-java and .sdkmanrc.
# Tests are skipped: CI's build job runs them, and here they would need Docker-in-Docker for Testcontainers.
FROM eclipse-temurin:25-jdk@sha256:ce2ef9d2d67a68de5e196d4de9d743327c44a10b57fe02ec39f572c652cacfd3 AS build
WORKDIR /build
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
COPY src/ src/
# The cache mount keeps Maven's repository (and the wrapper's Maven) between builds without putting it in a layer
RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw -B -ntp package -DskipTests \
 && cp target/build-bro-api-*.jar application.jar \
 && java -Djarmode=tools -jar application.jar extract --layers --destination extracted

# Runtime stage: distroless Java 25, no shell, no package manager, JRE only. Pinned by digest; Dependabot updates it.
FROM gcr.io/distroless/java25-debian13:nonroot@sha256:4618e7a1a1f5177222985347e1ffd503076f6be5837b89c8fd02800bd8551bde
LABEL org.opencontainers.image.source="https://github.com/esistegalaber/build-bro-api" \
      org.opencontainers.image.description="Build Bruh REST API: builds, build sets, servers and deployments" \
      org.opencontainers.image.licenses="Apache-2.0"
WORKDIR /app
# One layer each, least to most often changed: a code change doesn't re-ship the dependencies
COPY --from=build /build/extracted/dependencies/ ./
COPY --from=build /build/extracted/spring-boot-loader/ ./
COPY --from=build /build/extracted/snapshot-dependencies/ ./
COPY --from=build /build/extracted/application/ ./
# distroless's nonroot user, numeric so that Kubernetes' runAsNonRoot can verify it
USER 65532:65532
# JVM memory follows the container's limit; override at run time with -e JAVA_TOOL_OPTIONS=...
ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75"
EXPOSE 8080
# No database settings in the image: pass SPRING_DATASOURCE_URL, SPRING_DATASOURCE_USERNAME and
# SPRING_DATASOURCE_PASSWORD at run time. Exec form, so the JVM is PID 1 and gets SIGTERM for a graceful shutdown.
ENTRYPOINT ["/usr/bin/java", "-jar", "/app/application.jar"]
