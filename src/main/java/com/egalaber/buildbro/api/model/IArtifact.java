package com.egalaber.buildbro.api.model;


import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class IArtifact implements Serializable, Comparable<IArtifact> {
    private String project;
    String branch;
    Long buildNumber;
    Map<String, String> labels = new TreeMap<>();

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

    @Override
    public int compareTo(IArtifact other) {
        return getProject().compareTo(other.getProject());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IArtifact iArtifact = (IArtifact) o;
        return Objects.equals(getProject(), iArtifact.getProject()) && Objects.equals(getBranch(), iArtifact.getBranch()) && Objects.equals(getBuildNumber(), iArtifact.getBuildNumber()) && Objects.equals(getLabels(), iArtifact.getLabels());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProject(), getBranch(), getBuildNumber(), getLabels());
    }

    @Override
    public String toString() {
        return "IArtifact{" +
                "project='" + project + '\'' +
                ", branch='" + branch + '\'' +
                ", buildNumber=" + buildNumber +
                ", labels=" + labels +
                '}';
    }
}
