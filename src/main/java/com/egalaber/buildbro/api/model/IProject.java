package com.egalaber.buildbro.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;

@Schema(description = "A project and its branches.")
public record IProject(
        @Schema(description = "Project id", example = "1") Long id,
        @Schema(description = "Project name", example = "backend") String name,
        @Schema(description = "Inactive projects are left out of the search data", example = "true") Boolean active,
        @Schema(description = "Branches of the project") List<IBranch> branches
)
        implements Serializable, Comparable<IProject> {

    public IProject {
        branches = ModelCollections.list(branches);
    }

    @Override
    public int compareTo(IProject o) {
        return name().compareTo(o.name());
    }
}
