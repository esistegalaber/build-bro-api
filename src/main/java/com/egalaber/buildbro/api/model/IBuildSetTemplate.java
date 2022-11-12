package com.egalaber.buildbro.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class IBuildSetTemplate implements Serializable {
    private Long id;
    private String name;
    private List<IBuildTemplate> buildTemplates = new ArrayList<>();

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

    public List<IBuildTemplate> getBuildTemplates() {
        return buildTemplates;
    }

    public void setBuildTemplates(List<IBuildTemplate> buildTemplates) {
        this.buildTemplates = buildTemplates;
    }
}
