package com.egalaber.buildbro.api.impl;

import com.egalaber.buildbro.api.SearchDataEndpoint;
import com.egalaber.buildbro.api.model.ISearchData;
import com.egalaber.buildbro.core.service.SearchDataService;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@Transactional
public class SearchDataEndpointImpl implements SearchDataEndpoint {
    private final SearchDataService searchDataService;

    public SearchDataEndpointImpl(SearchDataService searchDataService) {
        this.searchDataService = searchDataService;
    }

    @Override
    public ISearchData getSearchData() {
        return searchDataService.searchData();
    }
}
