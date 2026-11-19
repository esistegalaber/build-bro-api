package com.egalaber.buildbro

import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.testcontainers.mariadb.MariaDBContainer
import org.testcontainers.utility.DockerImageName

/**
 * MariaDB started by Testcontainers for all Spring tests.
 * Pulled in with {@code @ImportTestcontainers(TestDatabase)}; {@code @ServiceConnection} points the
 * datasource at the container, overriding {@code spring.datasource.*} from application.properties.
 * The field is static, so one container is shared by every test context in the JVM.
 * The image comes from the {@code test.mariadb.image} Maven property (set in pom.xml, passed via Surefire).
 */
class TestDatabase {
    @ServiceConnection
    static final MariaDBContainer MARIADB = new MariaDBContainer(
            DockerImageName.parse(System.getProperty('test.mariadb.image', 'mariadb:10.10')))
}
