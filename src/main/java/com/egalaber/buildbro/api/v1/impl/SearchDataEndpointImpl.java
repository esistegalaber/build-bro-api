package com.egalaber.buildbro.api.v1.impl;

import com.egalaber.buildbro.api.model.ISearchData;
import com.egalaber.buildbro.api.v1.SearchDataApi;
import com.egalaber.buildbro.core.service.SearchDataService;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implements {@link SearchDataApi}, which is generated from the contract ({@code openapi/build-bro-api.yaml}).
 */
@RestController
@Transactional
public class SearchDataEndpointImpl implements SearchDataApi {
    private final SearchDataService searchDataService;

    public SearchDataEndpointImpl(SearchDataService searchDataService) {
        this.searchDataService = searchDataService;
    }

    @Override
    public ISearchData getSearchData() {
        return searchDataService.searchData();
    }
}
