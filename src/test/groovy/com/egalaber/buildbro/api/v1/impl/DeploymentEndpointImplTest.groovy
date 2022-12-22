package com.egalaber.buildbro.api.v1.impl

import com.egalaber.buildbro.api.BaseRestSpec
import com.egalaber.buildbro.api.model.IBuild
import com.egalaber.buildbro.api.model.IDeployment
import com.egalaber.buildbro.api.model.IDeploymentLabel
import com.egalaber.buildbro.api.model.IDeploymentSearch
import com.egalaber.buildbro.api.model.IDeploymentSearchResult
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class DeploymentEndpointImplTest extends BaseRestSpec {

    def "Search"() {
        given:
        IDeploymentSearch search = new IDeploymentSearch()
        String SEARCH_URL = "http://localhost:${port}/api/v1/deployments/search"

        when:
        ResponseEntity<IDeploymentSearchResult> responseEntity = restTemplate.postForEntity(SEARCH_URL, search, IDeploymentSearchResult)

        then:
        responseEntity.getStatusCode() == HttpStatus.OK

        and:
        !responseEntity.getBody().data.isEmpty()
    }

    def "Add"() {
        given:
        String serverName = "prod-1"
        IDeployment newDeployment = new IDeployment(
                serverName: serverName,
                builds: [new IBuild(id: 1), new IBuild(id: 2), new IBuild(id: 3)],
                labels: [new IDeploymentLabel(key: 'additional', value: 'info')]
        )
        String ADD_URL = "http://localhost:${port}/api/v1/deployments/${serverName}"

        when:
        ResponseEntity<IDeployment> responseEntity = restTemplate.postForEntity(ADD_URL, newDeployment, IDeployment)

        then:
        responseEntity.getStatusCode() == HttpStatus.OK

        and:
        responseEntity.getBody().builds.size() > 0

        and:
        responseEntity.getBody().labels.size() > 0
    }
}
