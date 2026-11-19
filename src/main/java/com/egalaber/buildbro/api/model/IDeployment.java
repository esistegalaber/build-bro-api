package com.egalaber.buildbro.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "A set of builds deployed to a server at one point in time.")
public record IDeployment(
        @Schema(description = "Deployment id, assigned by Build Bro", accessMode = Schema.AccessMode.READ_ONLY, example = "5") Long id,
        @Schema(description = "Name of the server deployed to; unknown servers are created", example = "staging-01") String serverName,
        @Schema(description = "When the deployment was recorded (UTC), set by Build Bro", accessMode = Schema.AccessMode.READ_ONLY) LocalDateTime deployedAt,
        @Schema(description = "Deployed builds. When recording a deployment only their `id` is used.") List<IBuild> builds,
        @Schema(description = "Labels of the deployment itself") List<IDeploymentLabel> labels
) implements Serializable {

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
