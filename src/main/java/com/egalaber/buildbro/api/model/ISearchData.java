package com.egalaber.buildbro.api.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public record ISearchData(List<String> projectNames, Map<String, List<String>> projectBranches,
                          List<String> labelKeys) implements Serializable {

    public ISearchData {
        projectNames = ModelCollections.list(projectNames);
        projectBranches = ModelCollections.map(projectBranches);
        labelKeys = ModelCollections.list(labelKeys);
    }
}
