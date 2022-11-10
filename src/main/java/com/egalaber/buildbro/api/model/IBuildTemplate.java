package com.egalaber.buildbro.api.model;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class IBuildTemplate implements Serializable {
    private Long id;
    private String project;
    private String branch;
    private Long buildNumber;
    private Map<String, String> labels = new TreeMap<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(Long buildNumber) {
        this.buildNumber = buildNumber;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }
}
