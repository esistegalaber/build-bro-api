package com.egalaber.buildbro.api.v1.impl;

import com.egalaber.buildbro.api.model.ISearchData;
import com.egalaber.buildbro.api.v1.BaseRestTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import spock.lang.Ignore;

@Ignore
class SearchDataEndpointImplTest extends BaseRestTest {
    @Test
    public void loadSearchData() {
        //given
        String GET_DATA_URL = baseUrl() + "/search-data";
        //when
        ResponseEntity<ISearchData> response = restTemplate.getForEntity(GET_DATA_URL, ISearchData.class);
        ISearchData searchData = response.getBody();
        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertFalse(response.getBody().getProjectNames().isEmpty());
    }
}
