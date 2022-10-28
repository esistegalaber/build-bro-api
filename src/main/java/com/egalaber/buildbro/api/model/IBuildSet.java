package com.egalaber.buildbro.api.model;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class IBuildSet implements Serializable {
    private String environment;
    private boolean internal;
    private Map<String, IBuild> builds = new TreeMap<>();

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public boolean isInternal() {
        return internal;
    }

    public void setInternal(boolean internal) {
        this.internal = internal;
    }

    public Map<String, IBuild> getBuilds() {
        return builds;
    }

    public void setBuilds(Map<String, IBuild> builds) {
        this.builds = builds;
    }
}
