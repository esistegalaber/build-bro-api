package com.egalaber.buildbro.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;

@Schema(description = "A build registered by a CI pipeline.")
public record IBuild(
        @Schema(description = "Build id, assigned by Build Bro. Ignored when registering a build; used to reference builds in deployments.", example = "42") Long id,
        @Schema(description = "Project name", example = "backend", requiredMode = Schema.RequiredMode.REQUIRED) String project,
        @Schema(description = "Branch name", example = "main", requiredMode = Schema.RequiredMode.REQUIRED) String branch,
        @Schema(description = "Build number, unique per project and branch", example = "17", requiredMode = Schema.RequiredMode.REQUIRED) Long buildNumber,
        @Schema(description = "Free-form key/value labels, e.g. git-sha or docker-image") List<IBuildLabel> labels
)
        implements Serializable {

    public IBuild {
        labels = ModelCollections.list(labels);
    }
}
