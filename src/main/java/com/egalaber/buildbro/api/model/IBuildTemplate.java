package com.egalaber.buildbro.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Map;

@Schema(description = "A rule selecting one build of a project: a pinned build number, or else the latest build of the branch carrying all given labels.")
public record IBuildTemplate(
        @Schema(description = "Template id, assigned by Build Bro", accessMode = Schema.AccessMode.READ_ONLY) Long id,
        @Schema(description = "Project name", example = "backend") String project,
        @Schema(description = "Branch name", example = "main") String branch,
        @Schema(description = "Pin to this build number; leave empty to select the latest matching build", example = "17") Long buildNumber,
        @Schema(description = "Labels the selected build must carry", example = "{\"qa\": \"passed\"}") Map<String, String> labels
) implements Serializable {

    public IBuildTemplate {
        labels = ModelCollections.sortedMap(labels);
    }
}
