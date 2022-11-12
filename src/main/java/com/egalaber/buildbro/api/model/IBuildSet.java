package com.egalaber.buildbro.api.model;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class IBuildSet implements Serializable {
    private String name;
    private Map<String, IBuild> builds = new TreeMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, IBuild> getBuilds() {
        return builds;
    }

    public void setBuilds(Map<String, IBuild> builds) {
        this.builds = builds;
    }
}
