package com.egalaber.buildbro.api.model;

import java.io.Serializable;

public class IBranch implements Serializable, Comparable<IBranch> {
    private Long id;
    private String name;
    private Boolean active;
    private Long projectId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    @Override
    public int compareTo(IBranch o) {
        return getName().compareTo(o.getName());
    }
}
