package com.egalaber.buildbro.api.model;

import java.io.Serializable;

public class IBranch implements Serializable, Comparable<IBranch> {
    private Long id;
    private String name;
    private Boolean active;

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

    @Override
    public int compareTo(IBranch o) {
        return getName().compareTo(o.getName());
    }
}
