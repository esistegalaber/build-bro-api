package com.egalaber.buildbro.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Map;

/**
 * The concrete builds a build-set template resolves to, keyed (and sorted) by project name.
 */
@Schema(description = "The concrete builds a build set resolves to.")
public record IBuildSet(
        @Schema(description = "Name of the build set, or `verification-result` for ad-hoc templates", example = "staging") String name,
        @Schema(description = "Selected build per project name") Map<String, IBuild> builds
) implements Serializable {

    public IBuildSet {
        builds = ModelCollections.sortedMap(builds);
    }
}
