package com.egalaber.buildbro.api.v1.impl

import com.egalaber.buildbro.api.BaseRestSpec
import com.egalaber.buildbro.api.model.IBuildBroStats
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class StatsEndpointImplSpec extends BaseRestSpec {
    def "Stats"() {
        given:
        String STATS_URL = "${baseUrl()}/stats"

        when:
        ResponseEntity<IBuildBroStats> responseEntity = restTemplate.getForEntity(STATS_URL, IBuildBroStats)

        then:
        responseEntity.statusCode == HttpStatus.OK
    }
}
