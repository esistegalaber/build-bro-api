package com.egalaber.buildbro.api.v1.impl

import com.egalaber.buildbro.api.BaseRestSpec
import com.egalaber.buildbro.api.model.IBranch
import com.egalaber.buildbro.api.model.IProject
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class ProjectEndpointImplSpec extends BaseRestSpec {

    def "GetProjectData"() {
        given:
        String VERIFY_URL = "${baseUrl()}/projects"
        when:
        ResponseEntity<List> responseEntity = restTemplate.getForEntity(VERIFY_URL, List)

        then:
        responseEntity.getStatusCode() == HttpStatus.OK

        and:
        responseEntity.getBody().size() > 0
    }

    def "Toggle Project active"() {
        given:
        Long projectId = 1L
        String ACTIVATE_URL = "${baseUrl()}/projects/active/${projectId}"
        String DE_ACTIVATE_URL = "${baseUrl()}/projects/inactive/${projectId}"
        String WRONG_ID_URL = "${baseUrl()}/projects/inactive/-1"
        when:
        ResponseEntity<IProject> deActivation = restTemplate.postForEntity(DE_ACTIVATE_URL, null, IProject)
        ResponseEntity<IProject> activation = restTemplate.postForEntity(ACTIVATE_URL, null, IProject)
        ResponseEntity<IProject> notFoundCall = restTemplate.postForEntity(WRONG_ID_URL, null, IProject)

        then:
        activation.getStatusCode() == HttpStatus.OK
        deActivation.getStatusCode() == HttpStatus.OK

        and:
        activation.getBody().active == true
        deActivation.getBody().active == false

        and:
        notFoundCall.getStatusCode() == HttpStatus.NOT_FOUND
    }

    def "Toggle Branch active"() {
        given:
        String branchId = 1L
        String ACTIVATE_URL = "${baseUrl()}/projects/branch-active/${branchId}"
        String DE_ACTIVATE_URL = "${baseUrl()}/projects/branch-inactive/${branchId}"
        String WRONG_ID_URL = "${baseUrl()}/projects/branch-inactive/-1"
        when:
        ResponseEntity<IBranch> deActivation = restTemplate.postForEntity(DE_ACTIVATE_URL, null, IBranch)
        ResponseEntity<IBranch> activation = restTemplate.postForEntity(ACTIVATE_URL, null, IBranch)
        ResponseEntity<IProject> notFoundCall = restTemplate.postForEntity(WRONG_ID_URL, null, IProject)

        then:
        activation.getStatusCode() == HttpStatus.OK
        deActivation.getStatusCode() == HttpStatus.OK

        and:
        activation.getBody().active == true
        deActivation.getBody().active == false

        and:
        notFoundCall.getStatusCode() == HttpStatus.NOT_FOUND
    }
}
