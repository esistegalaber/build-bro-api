package com.egalaber.buildbro.api.model;

import java.io.Serializable;

public class IBuildNumber implements Serializable {
    private String project;
    private String branch;
    private Long number;

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

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }
}