package com.egalaber.buildbro.api.model;

import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IBuildNumber that = (IBuildNumber) o;
        return Objects.equals(getProject(), that.getProject()) && Objects.equals(getBranch(), that.getBranch()) && Objects.equals(getNumber(), that.getNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProject(), getBranch(), getNumber());
    }

    @Override
    public String toString() {
        return "IBuildNumber{" +
                "project='" + project + '\'' +
                ", branch='" + branch + '\'' +
                ", number=" + number +
                '}';
    }
}
