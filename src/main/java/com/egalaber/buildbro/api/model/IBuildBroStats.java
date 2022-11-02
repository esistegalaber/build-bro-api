package com.egalaber.buildbro.api.model;

import java.io.Serializable;
import java.util.Objects;

public class IBuildBroStats implements Serializable {
    private Long numberOfProjects = 0L;
    private Long numberOfBranches = 0L;
    private Long numberOfBuilds = 0L;
    private Long numberOfLabels = 0L;
    private Long numberOfDeploys = 0L;

    public Long getNumberOfProjects() {
        return numberOfProjects;
    }

    public void setNumberOfProjects(Long numberOfProjects) {
        this.numberOfProjects = numberOfProjects;
    }

    public Long getNumberOfBranches() {
        return numberOfBranches;
    }

    public void setNumberOfBranches(Long numberOfBranches) {
        this.numberOfBranches = numberOfBranches;
    }

    public Long getNumberOfBuilds() {
        return numberOfBuilds;
    }

    public void setNumberOfBuilds(Long numberOfBuilds) {
        this.numberOfBuilds = numberOfBuilds;
    }

    public Long getNumberOfLabels() {
        return numberOfLabels;
    }

    public void setNumberOfLabels(Long numberOfLabels) {
        this.numberOfLabels = numberOfLabels;
    }

    public Long getNumberOfDeploys() {
        return numberOfDeploys;
    }

    public void setNumberOfDeploys(Long numberOfDeploys) {
        this.numberOfDeploys = numberOfDeploys;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IBuildBroStats that = (IBuildBroStats) o;
        return Objects.equals(getNumberOfProjects(), that.getNumberOfProjects()) && Objects.equals(getNumberOfBranches(), that.getNumberOfBranches()) && Objects.equals(getNumberOfBuilds(), that.getNumberOfBuilds()) && Objects.equals(getNumberOfLabels(), that.getNumberOfLabels()) && Objects.equals(getNumberOfDeploys(), that.getNumberOfDeploys());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumberOfProjects(), getNumberOfBranches(), getNumberOfBuilds(), getNumberOfLabels(), getNumberOfDeploys());
    }

    @Override
    public String toString() {
        return "IBuildBroStats{" +
                "numberOfProjects=" + numberOfProjects +
                ", numberOfBranches=" + numberOfBranches +
                ", numberOfBuilds=" + numberOfBuilds +
                ", numberOfLabels=" + numberOfLabels +
                ", numberOfDeploys=" + numberOfDeploys +
                '}';
    }
}
