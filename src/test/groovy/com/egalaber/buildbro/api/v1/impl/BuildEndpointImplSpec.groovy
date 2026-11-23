package com.egalaber.buildbro.api.v1.impl

import static com.egalaber.buildbro.api.model.ApiModels.*

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
        IBuildSearch search = aBuildSearch(
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
        IBuildSearch search = aBuildSearch(
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

    def "Search accepts the sort direction in any case: #sortDirection"() {
        given: 'raw JSON, as clients such as the UI send it (the UI sends lowercase)'
        String SEARCH_URL = "${baseUrl()}/builds/search"
        Map search = [project: 'backend', branch: 'main', sortDirection: sortDirection]

        when:
        ResponseEntity<IBuildSearchResult> responseEntity = restTemplate.postForEntity(SEARCH_URL, search, IBuildSearchResult)

        then:
        responseEntity.statusCode == HttpStatus.OK

        and:
        List<Long> buildNumbers = responseEntity.getBody().data*.buildNumber
        buildNumbers.size() > 1
        buildNumbers == (ascending ? buildNumbers.sort(false) : buildNumbers.sort(false).reverse())

        where:
        sortDirection | ascending
        'asc'         | true
        'ASC'         | true
        'desc'        | false
        'Desc'        | false
    }

    def "Search rejects an unknown sort direction"() {
        when:
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity("${baseUrl()}/builds/search", [sortDirection: 'sideways'], Map)

        then:
        responseEntity.statusCode == HttpStatus.BAD_REQUEST
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
        IBuild build = aBuild(
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
