package com.egalaber.buildbro.api.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public class IBuildSearchResult extends SearchResult<IBuild> {
    public IBuildSearchResult() {
        //Default Constructor needed for JSON generating libraries
    }

}
