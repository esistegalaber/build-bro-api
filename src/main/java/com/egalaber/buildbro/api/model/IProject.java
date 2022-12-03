package com.egalaber.buildbro.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class IProject implements Serializable, Comparable<IProject> {
    private Long id;
    private String name;
    private Boolean active;
    private List<IBranch> branches = new ArrayList<>();

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

    public List<IBranch> getBranches() {
        return branches;
    }

    public void setBranches(List<IBranch> branches) {
        this.branches = branches;
    }

    @Override
    public int compareTo(IProject o) {
        return getName().compareTo(o.getName());
    }
}
