package com.egalaber.buildbro.api.model;

import java.io.Serializable;

public record IBranch(Long id, String name, Boolean active, Long projectId)
        implements Serializable, Comparable<IBranch> {

    @Override
    public int compareTo(IBranch o) {
        return name().compareTo(o.name());
    }
}
