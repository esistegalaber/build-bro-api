package com.egalaber.buildbro.core.service

import com.egalaber.buildbro.BaseBuildBroSpec
import com.egalaber.buildbro.api.model.ISearchData
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Subject

class SearchDataServiceTest extends BaseBuildBroSpec {
    @Subject
    @Autowired
    SearchDataService service

    def "SearchData"() {
        when:
        ISearchData searchData = service.searchData()

        then:
        !searchData.projectNames.isEmpty()
        and:
        !searchData.projectBranches.isEmpty()
        and:
        !searchData.labelKeys.isEmpty()

    }

}
