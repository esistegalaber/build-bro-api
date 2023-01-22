package com.egalaber.buildbro.api.v1.impl

import com.egalaber.buildbro.api.BaseRestSpec
import com.egalaber.buildbro.api.model.*
import com.egalaber.buildbro.core.domain.BuildLabel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class DeploymentEndpointImplSpec extends BaseRestSpec {
    def "Search"() {
        given:
        IDeploymentSearch search = new IDeploymentSearch()
        String SEARCH_URL = "${baseUrl()}/deployments/search"

        when:
        ResponseEntity<IDeploymentSearchResult> responseEntity = restTemplate.postForEntity(SEARCH_URL, search, IDeploymentSearchResult)

        then:
        responseEntity.getStatusCode() == HttpStatus.OK

        and:
        !responseEntity.getBody().data.isEmpty()
    }

    def "CreateDeployment"() {
        given:
        String CREATE_URL = "${baseUrl()}/deployments"
        String serverName = "prod-1"
        IDeployment newDeployment = new IDeployment(
                serverName: serverName,
                builds: [new IBuild(id: 1), new IBuild(id: 2), new IBuild(id: 3)],
                labels: [new IDeploymentLabel(key: 'additional', value: 'info')]
        )

        when:
        ResponseEntity<IDeployment> responseEntity = restTemplate.postForEntity(CREATE_URL, newDeployment, IDeployment)

        then:
        responseEntity.getStatusCode() == HttpStatus.OK

        and:
        responseEntity.getBody().builds.size() > 0

        and:
        responseEntity.getBody().labels.size() > 0
    }

    def "CurrentDeployment"() {
        given:
        String CURRENT_URL = "${baseUrl()}/deployments/testserver"

        when:
        ResponseEntity<IDeployment> current = restTemplate.getForEntity(CURRENT_URL, IDeployment.class)

        then:
        current.getStatusCode() == HttpStatus.OK

        and:
        !current.getBody().getBuilds().isEmpty()
    }

    def "AddLabelsToBuilds"() {
        given:
        String ADD_BUILD_LABELS_URL = "${baseUrl()}/deployments/add-labels-to-builds/1"
        def labelList = [
                new BuildLabel(key: 'test.42.status', value: 'broken'),
                new BuildLabel(key: 'test.42.triggered', value: 'josip')
        ]
        when:
        ResponseEntity<IDeployment> updated = restTemplate.postForEntity(ADD_BUILD_LABELS_URL, labelList, IDeployment.class)

        then:
        updated.getStatusCode() == HttpStatus.OK

        and:
        !updated.getBody().getBuilds().isEmpty()
    }
}
