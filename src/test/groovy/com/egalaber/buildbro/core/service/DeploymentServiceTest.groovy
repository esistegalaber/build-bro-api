package com.egalaber.buildbro.core.service

import com.egalaber.buildbro.BaseBuildBroSpec
import com.egalaber.buildbro.api.model.IDeployment
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Subject

class DeploymentServiceTest extends BaseBuildBroSpec {
    @Subject
    @Autowired
    DeploymentService deploymentService

    def "Create"() {
        given:
        List<Long> buildIds = [1L, 2L, 3L]
        when:
        IDeployment created = deploymentService.create("prod-1", buildIds)

        then:
        created.id
        and:
        created.deployedAt
        and:
        created.builds.size() == 3
    }

    def "Create on new Server"() {
        given:
        List<Long> buildIds = [1L, 2L, 3L]
        when:
        IDeployment created = deploymentService.create("new-prod-3", buildIds)

        then:
        created.id
        and:
        created.deployedAt
        and:
        created.builds.size() == 3
    }
}
