package com.egalaber.buildbro.api.v1.impl

import com.egalaber.buildbro.api.BaseRestSpec
import com.egalaber.buildbro.api.model.IBuildSet
import com.egalaber.buildbro.api.model.IBuildSetTemplate
import com.egalaber.buildbro.api.model.IBuildTemplate
import com.egalaber.buildbro.api.model.IExceptionInfo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class BuildSetEndpointImplSpec extends BaseRestSpec {
    def "Create new, update and delete it"() {
        given:
        String newEnvName = 'new-crazy-env'
        String SAVE_URL = "${baseUrl()}/build-sets"
        String DELETE_URL = "${baseUrl()}/build-sets/${newEnvName}"
        IBuildSetTemplate newEnvironment = new IBuildSetTemplate(
                name: newEnvName,
                buildTemplates: [
                        new IBuildTemplate(
                                project: 'backend',
                                branch: 'main'
                        )
                ]
        )

        when:
        ResponseEntity<IBuildSetTemplate> created = restTemplate.postForEntity(SAVE_URL, newEnvironment, IBuildSetTemplate)
        IBuildSetTemplate toUpdate = created.getBody()
        toUpdate.getBuildTemplates().add(
                new IBuildTemplate(
                        project: 'frontend',
                        branch: 'main',
                        labels: ['integration-test': 'ok']
                )

        )
        ResponseEntity<IBuildSetTemplate> updated = restTemplate.postForEntity(SAVE_URL, toUpdate, IBuildSetTemplate)

        restTemplate.delete(DELETE_URL)

        then:
        created.statusCode == HttpStatus.OK
        and:
        updated.statusCode == HttpStatus.OK
        and:
        created.getBody().getId() == updated.getBody().getId()
    }

    def "Get"() {
        given:
        String envName = 'main'
        String GET_URL = "${baseUrl()}/build-sets/${envName}"

        when:
        ResponseEntity<IBuildSetTemplate> responseEntity = restTemplate.getForEntity(GET_URL, IBuildSetTemplate)

        then:
        responseEntity.statusCode == HttpStatus.OK

        and:
        responseEntity.getBody().id
    }

    def "Get DataNotFound"() {
        given:
        String envName = 'wrongName'
        String GET_URL = "${baseUrl()}/build-sets/${envName}"

        when:
        ResponseEntity<IExceptionInfo> responseEntity = restTemplate.getForEntity(GET_URL, IExceptionInfo)

        then:
        responseEntity.statusCode == HttpStatus.NOT_FOUND

        and:
        responseEntity.getBody().key == 'not-found'
    }

    def "List all BuildSet names"() {
        given:
        String LIST_URL = "${baseUrl()}/build-sets/names"

        when:
        ResponseEntity<List> responseEntity = restTemplate.getForEntity(LIST_URL, List)

        then:
        responseEntity.statusCode == HttpStatus.OK

        and:
        !responseEntity.body.isEmpty()
    }

    def "VerifyEnvironment with 1 articfact"() {
        given:
        String VERIFY_URL = "${baseUrl()}/build-sets/verify"
        IBuildTemplate artifact = new IBuildTemplate(
                project: 'backend',
                branch: 'main',
                labels: ['integration-test': 'ok']
        )
        when:
        ResponseEntity<IBuildSet> responseEntity = restTemplate.postForEntity(VERIFY_URL, [artifact], IBuildSet)

        then:
        responseEntity.getStatusCode() == HttpStatus.OK

        and:
        responseEntity.getBody().builds.size() == 1
    }

    def "BuildSet of defined environment"() {
        given:
        String templateName = 'main'
        String VERIFY_URL = "${baseUrl()}/build-sets/of/${templateName}"
        when:
        ResponseEntity<IBuildSet> responseEntity = restTemplate.getForEntity(VERIFY_URL, IBuildSet)

        then:
        responseEntity.getStatusCode() == HttpStatus.OK

        and:
        responseEntity.getBody().builds.size() == 2
    }

    def "all BuildSets"() {
        given:
        String GET_URL = "${baseUrl()}/build-sets"

        when:
        ResponseEntity<List> responseEntity = restTemplate.getForEntity(GET_URL, List)

        then:
        responseEntity.getStatusCode() == HttpStatus.OK

        and:
        responseEntity.getBody().size() > 0
    }
}
