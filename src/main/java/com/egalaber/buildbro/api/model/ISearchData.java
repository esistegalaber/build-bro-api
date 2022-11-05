package com.egalaber.buildbro.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ISearchData implements Serializable {
    private List<String> projectNames = new ArrayList<>();
    private Map<String, List<String>> projectBranches = new HashMap<>();
    private List<String> labelKeys = new ArrayList<>();

    public List<String> getProjectNames() {
        return projectNames;
    }

    public void setProjectNames(List<String> projectNames) {
        this.projectNames = projectNames;
    }

    public Map<String, List<String>> getProjectBranches() {
        return projectBranches;
    }

    public void setProjectBranches(Map<String, List<String>> projectBranches) {
        this.projectBranches = projectBranches;
    }

    public List<String> getLabelKeys() {
        return labelKeys;
    }

    public void setLabelKeys(List<String> labelKeys) {
        this.labelKeys = labelKeys;
    }
}
