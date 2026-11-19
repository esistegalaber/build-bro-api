package com.egalaber.buildbro.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Schema(description = "Values for building search forms.")
public record ISearchData(
        @Schema(description = "Names of all active projects") List<String> projectNames,
        @Schema(description = "Branch names per active project") Map<String, List<String>> projectBranches,
        @Schema(description = "Every label key used on any build") List<String> labelKeys
) implements Serializable {

    public ISearchData {
        projectNames = ModelCollections.list(projectNames);
        projectBranches = ModelCollections.map(projectBranches);
        labelKeys = ModelCollections.list(labelKeys);
    }
}
