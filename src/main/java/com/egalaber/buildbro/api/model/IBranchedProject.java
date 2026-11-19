package com.egalaber.buildbro.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

@Schema(description = "A project/branch pair.")
public record IBranchedProject(
        @Schema(description = "Project name", example = "backend") String project,
        @Schema(description = "Branch name", example = "main") String branch
) implements Serializable {
}
