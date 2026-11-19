package com.egalaber.buildbro.api.model;

import java.io.Serializable;

public record IDeploymentLabel(Long id, String key, String value) implements Serializable, Comparable<IDeploymentLabel> {

    @Override
    public int compareTo(IDeploymentLabel other) {
        return key().compareTo(other.key());
    }
}
