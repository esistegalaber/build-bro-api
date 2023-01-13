package com.egalaber.buildbro.core.service

import com.egalaber.buildbro.BaseBuildBroSpec
import com.egalaber.buildbro.api.fault.DataNotFoundException
import com.egalaber.buildbro.api.model.IBuild
import com.egalaber.buildbro.api.model.IDeployment
import com.egalaber.buildbro.api.model.IDeploymentSearch
import com.egalaber.buildbro.api.model.IDeploymentSearchResult
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Subject
import spock.lang.Unroll

class DeploymentServiceTest extends BaseBuildBroSpec {
    @Subject
    @Autowired
    DeploymentService deploymentService

    def "Create"() {
        given:
        String serverName = "prod-1"
        IDeployment newDeployment = new IDeployment(
                serverName: serverName,
                builds: [new IBuild(id: 1), new IBuild(id: 2), new IBuild(id: 3)]
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
        IDeployment newDeployment = new IDeployment(
                serverName: serverName,
                builds: [new IBuild(id: 1), new IBuild(id: 2), new IBuild(id: 3)]
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
        IDeploymentSearch search = new IDeploymentSearch()
        search.setServerName(serverName)

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
