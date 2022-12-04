package com.egalaber.buildbro.api.v1;

import com.egalaber.buildbro.api.model.ISearchData;
import org.springframework.web.bind.annotation.GetMapping;

public interface SearchDataEndpoint {
    @GetMapping("/api/v1/search-data")
    ISearchData getSearchData();
}
