package com.egalaber.buildbro.api.impl

import com.egalaber.buildbro.api.BaseRestSpec
import com.egalaber.buildbro.api.model.IBuildNumber
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class BuildNumberEndpointImplTest extends BaseRestSpec {

    def "Current() for existing project"() {
        given:
        String project = 'backend'
        String branch = 'main'
        String GET_CURRENT_URL = "http://localhost:${port}/api/v1/build-numbers/current/${project}/${branch}"

        when:
        ResponseEntity<IBuildNumber> current = restTemplate.getForEntity(GET_CURRENT_URL, IBuildNumber)
        IBuildNumber buildNumber = current.getBody()

        then:
        current.statusCode == HttpStatus.OK

        and:
        buildNumber.number == 10
    }

    def "Current() on new project"() {
        given:
        String project = 'backend'
        String branch = 'master'
        String GET_CURRENT_URL = "http://localhost:${port}/api/v1/build-numbers/current/${project}/${branch}"

        when:
        ResponseEntity<IBuildNumber> first = restTemplate.getForEntity(GET_CURRENT_URL, IBuildNumber)
        ResponseEntity<IBuildNumber> second = restTemplate.getForEntity(GET_CURRENT_URL, IBuildNumber)

        then:
        first.statusCode == HttpStatus.OK

        and:
        second.statusCode == HttpStatus.OK
        first.getBody().number == second.getBody().number

    }

    def "Next() and Set()"() {
        given:
        String project = 'backend'
        String branch = 'feature/feature-backend'
        IBuildNumber nextBuildCount = new IBuildNumber(
                project: project,
                branch: branch
        )
        IBuildNumber setBuildCount = new IBuildNumber(
                project: project,
                branch: branch,
                number: 20
        )

        String NEXT_URL = "http://localhost:${port}/api/v1/build-numbers/next"
        String SET_URL = "http://localhost:${port}/api/v1/build-numbers/set"

        when:
        //Call next
        ResponseEntity<IBuildNumber> nextResponse = restTemplate.postForEntity(NEXT_URL, nextBuildCount, IBuildNumber)
        ResponseEntity<IBuildNumber> setResponse = restTemplate.postForEntity(SET_URL, setBuildCount, IBuildNumber)

        then:
        nextResponse.statusCode == HttpStatus.OK
        setResponse.statusCode == HttpStatus.OK

        and:
        nextResponse.getBody().number == 11
        setResponse.getBody().number == 20
    }
}
