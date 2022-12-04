package com.egalaber.buildbro.api.v1.impl

import com.egalaber.buildbro.api.BaseRestSpec
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class ProjectEndpointImplTest extends BaseRestSpec {
    def "GetProjectData"() {
        given:
        String VERIFY_URL = "http://localhost:${port}/api/v1/projects"
        when:
        ResponseEntity<List> responseEntity = restTemplate.getForEntity(VERIFY_URL, List)

        then:
        responseEntity.getStatusCode() == HttpStatus.OK

        and:
        responseEntity.getBody().size() > 0
    }
}
