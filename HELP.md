# Read Me First
* The original package name 'com.egalaber.build-bro-api' is invalid and this project uses 'com.egalaber.buildbroapi' instead.

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.0.2/maven-plugin/reference/htmlsingle/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.0.2/maven-plugin/reference/htmlsingle/#build-image)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.7.5/reference/htmlsingle/#data.sql.jpa-and-spring-data)
* [Flyway Migration](https://docs.spring.io/spring-boot/docs/2.7.5/reference/htmlsingle/#howto.data-initialization.migration-tool.flyway)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.7.5/reference/htmlsingle/#web)

### Guides
The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Docker Setup
docker buildx create --name build-bruh-builder --driver docker-container --bootstrap
docker buildx use build-bruh-builder
docker buildx build --platform linux/amd64,linux/arm64 -t esistegalaber/build-bruh-api:0.1.0 --push .
docker buildx build --platform linux/amd64,linux/arm64 -t esistegalaber/build-bruh-ui:0.2.2 --push .


docker run --network=buildz_net -d -e DB_URL=jdbc:mariadb://buildz_db:3306/buildz -p 8080:8080  esistegalaber/build-bruh-api:0.1.0
docker run --name bruh-api -d -p 80bu80:8080 --network buildz_net -e DB_URL=jdbc:mariadb://buildz_db:3306/buildz esistegalaber/build-bruh-api:0.1.0