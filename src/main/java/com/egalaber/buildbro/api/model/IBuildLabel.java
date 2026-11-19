package com.egalaber.buildbro.api.model;

import java.io.Serializable;

public record IBuildLabel(Long id, String key, String value) implements Serializable, Comparable<IBuildLabel> {

    @Override
    public int compareTo(IBuildLabel other) {
        return key().compareTo(other.key());
    }
}
