package com.egalaber.buildbro.api.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public record IDeployment(Long id, String serverName, LocalDateTime deployedAt, List<IBuild> builds,
                          List<IDeploymentLabel> labels) implements Serializable {

    public IDeployment {
        builds = ModelCollections.list(builds);
        labels = ModelCollections.list(labels);
    }

    @Override
    public String toString() {
        // builds and labels left out on purpose: they can be long
        return "IDeployment[id=" + id + ", serverName=" + serverName + ", deployedAt=" + deployedAt + "]";
    }
}
