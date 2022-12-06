package com.egalaber.buildbro.api.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public class IDeploymentSearchResult extends SearchResult<IDeployment> {
    public IDeploymentSearchResult() {
        //Default Constructor needed for JSON generating libraries
    }

}
