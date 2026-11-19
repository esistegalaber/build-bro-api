package com.egalaber.buildbro.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

@Schema(description = "Overall counts.")
public record IBuildBroStats(
        @Schema(description = "Number of projects", example = "4") Long numberOfProjects,
        @Schema(description = "Number of branches", example = "12") Long numberOfBranches,
        @Schema(description = "Number of builds", example = "340") Long numberOfBuilds,
        @Schema(description = "Number of build labels", example = "910") Long numberOfLabels,
        @Schema(description = "Number of deployments (not counted yet, always 0)", example = "0") Long numberOfDeploys
) implements Serializable {

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
