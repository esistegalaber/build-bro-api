package com.egalaber.buildbro.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

@Schema(description = "A branch of a project.")
public record IBranch(
        @Schema(description = "Branch id", example = "1") Long id,
        @Schema(description = "Branch name", example = "main") String name,
        @Schema(description = "Informational flag, e.g. false for merged branches", example = "true") Boolean active,
        @Schema(description = "Id of the project the branch belongs to", example = "1") Long projectId
)
        implements Serializable, Comparable<IBranch> {

    @Override
    public int compareTo(IBranch o) {
        return name().compareTo(o.name());
    }
}
