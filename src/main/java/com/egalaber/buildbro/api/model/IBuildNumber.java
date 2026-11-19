package com.egalaber.buildbro.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

@Schema(description = "The build number counter of a project/branch.")
public record IBuildNumber(
        @Schema(description = "Project name", example = "backend") String project,
        @Schema(description = "Branch name", example = "main") String branch,
        @Schema(description = "Last build number handed out; 0 if none yet", example = "17") Long number
) implements Serializable {

    public IBuildNumber {
        number = number == null ? 0L : number;
    }

    /** A build number that has not been counted yet (number 0). */
    public IBuildNumber(String project, String branch) {
        this(project, branch, 0L);
    }
}
