# syntax=docker/dockerfile:1
FROM azul/zulu-openjdk-alpine:17-latest

ENV DB_URL="jdbc:mariadb://localhost:3306/buildz"
ENV DB_USR="buildz"
ENV DB_PWD="buildz"

ADD build/libs/build-bro-api-0.2.0.jar /opt/buildz-api.jar

EXPOSE 8080
CMD ["java", "-jar", "/opt/buildz-api.jar", "--spring.datasource.url=${DB_URL}", "--spring.datasource.username=${DB_USR}", "--spring.datasource.password=${DB_PWD}"]