package com.egalaber.buildbro.api.v1;

import com.egalaber.buildbro.api.fault.DataNotFoundException;
import com.egalaber.buildbro.api.model.IBranch;
import com.egalaber.buildbro.api.model.IProject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Tag(name = "Projects", description = """
        Projects and their branches. They are created automatically when the first build for them is \
        registered. Inactive projects are left out of the search data; the active flag of a branch is \
        informational, e.g. to mark merged branches.""")
public interface ProjectEndpoint {

    @Operation(
            operationId = "listProjects",
            summary = "List projects", description = "All projects with their branches, including inactive ones.")
    @GetMapping("/api/v1/projects")
    List<IProject> getProjectData();

    @Operation(
            operationId = "activateProject",
            summary = "Activate a project")
    @PostMapping("/api/v1/projects/active/{projectId}")
    IProject projectActive(@Parameter(description = "Project id", example = "1") @PathVariable("projectId") Long projectId)
            throws DataNotFoundException;

    @Operation(
            operationId = "deactivateProject",
            summary = "Deactivate a project")
    @PostMapping("/api/v1/projects/inactive/{projectId}")
    IProject projectInactive(@Parameter(description = "Project id", example = "1") @PathVariable("projectId") Long projectId)
            throws DataNotFoundException;

    @Operation(
            operationId = "activateBranch",
            summary = "Activate a branch")
    @PostMapping("/api/v1/projects/branch-active/{branchId}")
    IBranch branchActive(@Parameter(description = "Branch id", example = "1") @PathVariable("branchId") Long branchId)
            throws DataNotFoundException;

    @Operation(
            operationId = "deactivateBranch",
            summary = "Deactivate a branch")
    @PostMapping("/api/v1/projects/branch-inactive/{branchId}")
    IBranch branchInactive(@Parameter(description = "Branch id", example = "1") @PathVariable("branchId") Long branchId)
            throws DataNotFoundException;
}
