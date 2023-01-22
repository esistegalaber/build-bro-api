package com.egalaber.buildbro.api.v1.impl

import com.egalaber.buildbro.api.BaseRestSpec
import com.egalaber.buildbro.api.model.IBuild
import com.egalaber.buildbro.api.model.IBuildSearch
import com.egalaber.buildbro.api.model.IBuildSearchResult
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class BuildEndpointImplSpec extends BaseRestSpec {
    def "Search"() {
        given:
        String SEARCH_URL = "${baseUrl()}/builds/search"
        IBuildSearch search = new IBuildSearch(
                project: 'backend'
        )

        when:
        ResponseEntity<IBuildSearchResult> responseEntity = restTemplate.postForEntity(SEARCH_URL, search, IBuildSearchResult)

        then:
        responseEntity.statusCode == HttpStatus.OK

        and:
        !responseEntity.getBody().data.isEmpty()
    }

    def "Search with Labels"() {
        given:
        String SEARCH_URL = "${baseUrl()}/builds/search"
        IBuildSearch search = new IBuildSearch(
                project: 'backend',
                labels: [
                        'integration-test': 'broken'
                ]
        )

        when:
        ResponseEntity<IBuildSearchResult> responseEntity = restTemplate.postForEntity(SEARCH_URL, search, IBuildSearchResult)

        then:
        responseEntity.statusCode == HttpStatus.OK

        and:
        !responseEntity.getBody().data.isEmpty()
    }

    def "Get"() {
        given:
        Long buildId = 1
        String GET_URL = "${baseUrl()}/builds/${buildId}"

        when:
        ResponseEntity<IBuild> responseEntity = restTemplate.getForEntity(GET_URL, IBuild)

        then:
        responseEntity.statusCode == HttpStatus.OK

        and:
        responseEntity.getBody().id == buildId
    }

    def "Get non existing Build"() {
        given:
        Long buildId = -1
        String GET_URL = "${baseUrl()}/builds/${buildId}"

        when:
        ResponseEntity<IBuild> responseEntity = restTemplate.getForEntity(GET_URL, IBuild)

        then:
        responseEntity.statusCode == HttpStatus.NOT_FOUND
    }

    def "Create"() {
        given:
        String CREATE_URL = "${baseUrl()}/builds/create"
        IBuild build = new IBuild(
                project: 'backend',
                branch: 'hotfix',
                buildNumber: 1
        )

        when:
        ResponseEntity<IBuild> responseEntity = restTemplate.postForEntity(CREATE_URL, build, IBuild)

        then:
        responseEntity.statusCode == HttpStatus.OK

        and:
        responseEntity.getBody().id
    }

    def "AddLabels"() {
        given:
        Long buildId = 1
        String ADD_LABEL_URL = "${baseUrl()}/builds/add-labels/${buildId}"
        Map<String, String> newLabels = [
                'crazy-key': 'crazy-value'
        ]

        when:
        ResponseEntity<IBuild> responseEntity = restTemplate.postForEntity(ADD_LABEL_URL, newLabels, IBuild)

        then:
        responseEntity.statusCode == HttpStatus.OK

        and:
        responseEntity.getBody().labels.collect { it.key }.contains('crazy-key')
    }
}
