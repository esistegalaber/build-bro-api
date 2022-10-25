package com.egalaber.buildbro.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IBuild implements Serializable {
    private Long id;
    private String project;
    private String branch;
    private Long buildNumber;
    private List<IBuildLabel> labels = new ArrayList<>();

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

    public List<IBuildLabel> getLabels() {
        return labels;
    }

    public void setLabels(List<IBuildLabel> labels) {
        this.labels = labels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IBuild iBuild = (IBuild) o;
        return Objects.equals(getId(), iBuild.getId()) && Objects.equals(getProject(), iBuild.getProject()) && Objects.equals(getBranch(), iBuild.getBranch()) && Objects.equals(getBuildNumber(), iBuild.getBuildNumber()) && Objects.equals(getLabels(), iBuild.getLabels());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getProject(), getBranch(), getBuildNumber(), getLabels());
    }

    @Override
    public String toString() {
        return "IBuild{" +
                "id=" + id +
                ", project='" + project + '\'' +
                ", branch='" + branch + '\'' +
                ", buildNumber=" + buildNumber +
                ", labels=" + labels +
                '}';
    }
}
