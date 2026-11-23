package com.egalaber.buildbro.api.v1.impl

import com.egalaber.buildbro.api.BaseRestSpec
import com.egalaber.buildbro.api.model.ISearchData
import com.egalaber.buildbro.api.model.LabelStyle
import com.egalaber.buildbro.api.model.LabelType
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class SearchDataEndpointImplSpec extends BaseRestSpec {
    def "SearchData()"() {
        given:
        String GET_CURRENT_URL = "${baseUrl()}/search-data"

        when:
        ResponseEntity<ISearchData> response = restTemplate.getForEntity(GET_CURRENT_URL, ISearchData)
        ISearchData searchData = response.getBody()

        then:
        response.statusCode == HttpStatus.OK

        and:
        !searchData.projectNames.isEmpty()

        and: 'the label definitions, typed'
        searchData.labelDefinitions*.key == ['integration-test', 'jenkins-build', 'revision']
        searchData.labelDefinitions[0].type == LabelType.ENUM
        searchData.labelDefinitions[0].valueStyles == [ok: LabelStyle.SUCCESS, broken: LabelStyle.ERROR]
    }
}
