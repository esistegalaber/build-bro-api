package com.egalaber.buildbro.core.service

import static com.egalaber.buildbro.api.model.ApiModels.aBuild
import static com.egalaber.buildbro.api.model.ApiModels.aDeployment
import static com.egalaber.buildbro.api.model.ApiModels.aDeploymentSearch

import com.egalaber.buildbro.BaseBuildBroSpec
import com.egalaber.buildbro.api.fault.DataNotFoundException
import com.egalaber.buildbro.api.model.IDeployment
import com.egalaber.buildbro.api.model.IDeploymentSearch
import com.egalaber.buildbro.api.model.IDeploymentSearchResult
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import spock.lang.Subject
import spock.lang.Unroll

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit

class DeploymentServiceTest extends BaseBuildBroSpec {
    @Subject
    @Autowired
    DeploymentService deploymentService

    @Autowired
    EntityManager entityManager

    @Autowired
    JdbcTemplate jdbcTemplate

    def "deployedAt is stored in UTC, whatever the JVM's time zone"() {
        given: 'a JVM far from UTC'
        TimeZone original = TimeZone.getDefault()
        TimeZone.setDefault(TimeZone.getTimeZone('Pacific/Kiritimati')) // UTC+14
        Instant before = Instant.now().truncatedTo(ChronoUnit.SECONDS)

        when:
        IDeployment created = deploymentService.create(aDeployment(serverName: 'prod-1', builds: [aBuild(id: 1)]))
        entityManager.flush()
        LocalDateTime column = jdbcTemplate.queryForObject(
                'SELECT deployed_at FROM deployment WHERE id = ?', LocalDateTime, created.id)

        then: 'the column holds the UTC wall-clock time of the instant'
        column.toInstant(ZoneOffset.UTC) == created.deployedAt.truncatedTo(ChronoUnit.SECONDS)
        and: 'that instant is now'
        !created.deployedAt.isBefore(before)
        !created.deployedAt.isAfter(Instant.now())

        cleanup:
        TimeZone.setDefault(original)
    }

    def "Create"() {
        given:
        String serverName = "prod-1"
        IDeployment newDeployment = aDeployment(
                serverName: serverName,
                builds: [aBuild(id: 1), aBuild(id: 2), aBuild(id: 3)]
        )
        when:
        IDeployment created = deploymentService.create(newDeployment)

        then:
        created.id
        and:
        created.deployedAt
        and:
        created.builds.size() == 3
    }

    def "Create on new Server"() {
        given:
        String serverName = "new-prod-3"
        IDeployment newDeployment = aDeployment(
                serverName: serverName,
                builds: [aBuild(id: 1), aBuild(id: 2), aBuild(id: 3)]
        )
        when:
        IDeployment created = deploymentService.create(newDeployment)

        then:
        created.id
        and:
        created.deployedAt
        and:
        created.builds.size() == 3
    }

    @Unroll("#message")
    def "Search"() {
        given:
        IDeploymentSearch search = aDeploymentSearch(serverName: serverName)

        when:
        IDeploymentSearchResult result = deploymentService.search(search)

        then:
        result
        and:
        result.data.size() == extecedResultSize

        where:
        serverName     | extecedResultSize | message
        null           | 10                | 'Empty search working'
        'staging'      | 1                 | 'Search by serverName'
        'noSuchServer' | 0                 | 'Search by non-existing serverName'
    }

    def "Current"() {
        given: 'the server name'
        String serverName = 'testserver'

        when: 'load latest deployment'
        IDeployment deployment = deploymentService.current(serverName)

        then: 'the right one should be found'
        deployment.id == 1
    }

    def "Current not finding data"() {
        given: 'the server name'
        String serverName = 'prod-1'

        when: 'load latest deployment'
        deploymentService.current(serverName)

        then: 'no deployment found'
        thrown(DataNotFoundException)
    }
}
