package com.egalaber.buildbro.api.v1;

import com.egalaber.buildbro.api.model.IBranchedProject;
import com.egalaber.buildbro.api.model.IBuildNumber;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Build numbers", description = """
        Build number counters, one per project/branch pair. Pipelines use these instead of the CI server's \
        job number, so numbering survives re-created jobs or a change of CI system.""")
public interface BuildNumberEndpoint {

    @Operation(
            operationId = "nextBuildNumber",
            summary = "Next build number",
            description = "Increments and returns the counter of the project/branch. The first call for a new pair returns 1.")
    @PostMapping("/api/v1/build-numbers/next")
    IBuildNumber next(@RequestBody IBranchedProject branchedProject);

    @Operation(
            operationId = "currentBuildNumber",
            summary = "Current build number",
            description = "Returns the counter without changing it; 0 if no number has been handed out yet.")
    @GetMapping("/api/v1/build-numbers/current/{project}/{branch}")
    IBuildNumber current(
            @Parameter(description = "Project name", example = "backend") @PathVariable(name = "project") String project,
            @Parameter(description = "Branch name", example = "main") @PathVariable(name = "branch") String branch);

    @Operation(
            operationId = "setBuildNumber",
            summary = "Set build number",
            description = "Sets the counter of a project/branch to the given number, e.g. to continue an existing numbering. "
                    + "The next call to 'Next build number' returns this number + 1.")
    @PostMapping("/api/v1/build-numbers/set")
    IBuildNumber set(@RequestBody IBuildNumber buildNumber);
}
