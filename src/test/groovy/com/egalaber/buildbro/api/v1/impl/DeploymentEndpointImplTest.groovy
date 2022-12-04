package com.egalaber.buildbro.api.v1.impl

import com.egalaber.buildbro.api.BaseRestSpec
import com.egalaber.buildbro.api.model.IDeployment
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class DeploymentEndpointImplTest extends BaseRestSpec {
    def "Add"() {
        given:
        String serverName = "prod-1"
        String ADD_URL = "http://localhost:${port}/api/v1/deployments/${serverName}"
        List<Long> buildIds = [1, 2, 3]
        when:
        ResponseEntity<IDeployment> responseEntity = restTemplate.postForEntity(ADD_URL, buildIds, IDeployment)

        then:
        responseEntity.getStatusCode() == HttpStatus.OK

        and:
        responseEntity.getBody().builds.size() > 0
    }
}
