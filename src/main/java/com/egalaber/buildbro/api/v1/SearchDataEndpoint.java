package com.egalaber.buildbro.api.v1;

import com.egalaber.buildbro.api.model.ISearchData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "Search data", description = "Values for building search forms in a UI.")
public interface SearchDataEndpoint {

    @Operation(
            operationId = "getSearchData",
            summary = "Known projects, branches and label keys",
            description = "Active projects with their branch names, plus every label key used on any build.")
    @GetMapping("/api/v1/search-data")
    ISearchData getSearchData();
}
