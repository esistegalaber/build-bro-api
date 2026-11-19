package com.egalaber.buildbro.api.model;

import java.io.Serializable;

public record IBuildBroStats(Long numberOfProjects, Long numberOfBranches, Long numberOfBuilds,
                             Long numberOfLabels, Long numberOfDeploys) implements Serializable {

    public IBuildBroStats {
        numberOfProjects = zeroIfNull(numberOfProjects);
        numberOfBranches = zeroIfNull(numberOfBranches);
        numberOfBuilds = zeroIfNull(numberOfBuilds);
        numberOfLabels = zeroIfNull(numberOfLabels);
        numberOfDeploys = zeroIfNull(numberOfDeploys);
    }

    private static Long zeroIfNull(Long value) {
        return value == null ? 0L : value;
    }
}
