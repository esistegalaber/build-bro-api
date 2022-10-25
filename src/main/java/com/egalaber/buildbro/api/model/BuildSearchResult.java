package com.egalaber.buildbro.api.model;

import com.fasterxml.jackson.databind.util.ArrayBuilders;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema
public class BuildSearchResult extends SearchResult<IBuild> {

    /**
     * Default Constructor needed for JSON generating libraries
     */
    public BuildSearchResult() {
    }

//    private BuildSearchResult(SearchResult sr) {
//        super(sr);
//    }

//    /**
//     * Generate a BuildSearchResult based on a query Page.
//     *
//     * @param result A page of a query
//     * @return the BuildSearchResult based on the query Page
//     */
//    static BuildSearchResult fromPageResult(Page<Build> result) {
//        new BuildSearchResult(fromPageResult(result, IBuild. & of))
//    }

//    List<IBuild> getBuilds() {
//        return super.results;
//    }
}
