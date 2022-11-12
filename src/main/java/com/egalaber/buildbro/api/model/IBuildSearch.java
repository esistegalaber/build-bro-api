package com.egalaber.buildbro.api.model;

import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.Map;

public class IBuildSearch extends BaseSearch {
    private static final String DEFAULT_SORT_ATTRIBUTE = "buildNumber";
    private String project;
    private String branch;
    private Long minBuildNumber;
    private Long maxBuildNumber;
    private Map<String, String> labels = new HashMap<>();

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Long getMinBuildNumber() {
        return minBuildNumber;
    }

    public void setMinBuildNumber(Long minBuildNumber) {
        this.minBuildNumber = minBuildNumber;
    }

    public Long getMaxBuildNumber() {
        return maxBuildNumber;
    }

    public void setMaxBuildNumber(Long maxBuildNumber) {
        this.maxBuildNumber = maxBuildNumber;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }

    public static IBuildSearch fromBuildTemplate(IBuildTemplate artifact) {
        IBuildSearch newBuildSearch = new IBuildSearch();
        newBuildSearch.setProject(artifact.getProject());
        newBuildSearch.setBranch(artifact.getBranch());
        newBuildSearch.setLabels(artifact.getLabels());
        newBuildSearch.setPageSize(1);
        newBuildSearch.setPage(0);
        newBuildSearch.setSortAttribute(DEFAULT_SORT_ATTRIBUTE);
        newBuildSearch.setSortDirection(Sort.Direction.DESC.name());

        if (artifact.getBuildNumber() != null && artifact.getBuildNumber() > 0) {
            // The min and max build number search params are exclusive
            // so to ensure that only the provided build number is
            // returned set the minBuildNumber to the buildNumber minus one
            // and the maxBuildNumber to the buildNumber plus one
            newBuildSearch.minBuildNumber = artifact.getBuildNumber() - 1;
            newBuildSearch.maxBuildNumber = artifact.getBuildNumber() + 1;
        }

        return newBuildSearch;
    }

    protected String defaultSortAttribute() {
        return DEFAULT_SORT_ATTRIBUTE;
    }
}
