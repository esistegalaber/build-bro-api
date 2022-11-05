package com.egalaber.buildbro.api.model;

import io.swagger.v3.oas.annotations.media.Schema;

public class IProjectStatus {
    private String name;
    private Boolean active;

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
}
