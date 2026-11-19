package com.egalaber.buildbro.api.model;

import java.io.Serializable;
import java.util.List;

public record IProject(Long id, String name, Boolean active, List<IBranch> branches)
        implements Serializable, Comparable<IProject> {

    public IProject {
        branches = ModelCollections.list(branches);
    }

    @Override
    public int compareTo(IProject o) {
        return name().compareTo(o.name());
    }
}
