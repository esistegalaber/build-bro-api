package com.egalaber.buildbro.api.v1.impl

import com.egalaber.buildbro.api.BaseRestSpec
import com.egalaber.buildbro.api.model.ISearchData
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Ignore

@Ignore
class SearchDataEndpointImplSpec extends BaseRestSpec {
    def "SearchData()"() {
        given:
        String GET_CURRENT_URL = "http://localhost:${port}/api/v1/search-data/"

        when:
        ResponseEntity<ISearchData> response = restTemplate.getForEntity(GET_CURRENT_URL, ISearchData)
        ISearchData searchData = response.getBody()

        then:
        response.statusCode == HttpStatus.OK

        and:
        !searchData.projectNames.isEmpty()
    }
}
