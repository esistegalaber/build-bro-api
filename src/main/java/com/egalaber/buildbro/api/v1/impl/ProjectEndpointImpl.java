package com.egalaber.buildbro.api.v1.impl;

import com.egalaber.buildbro.api.model.IBranch;
import com.egalaber.buildbro.api.model.IProject;
import com.egalaber.buildbro.api.v1.ProjectsApi;
import com.egalaber.buildbro.core.service.ProjectService;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Implements {@link ProjectsApi}, which is generated from the contract ({@code openapi/build-bro-api.yaml}).
 */
@RestController
@Transactional
public class ProjectEndpointImpl implements ProjectsApi {
    private final ProjectService projectService;

    public ProjectEndpointImpl(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    public List<IProject> listProjects() {
        return projectService.projectData(true);
    }

    @Override
    public IProject activateProject(Long projectId) {
        return projectService.setProjectActive(projectId, true);
    }

    @Override
    public IProject deactivateProject(Long projectId) {
        return projectService.setProjectActive(projectId, false);
    }

    @Override
    public IBranch activateBranch(Long branchId) {
        return projectService.setBranchActive(branchId, true);
    }

    @Override
    public IBranch deactivateBranch(Long branchId) {
        return projectService.setBranchActive(branchId, false);
    }
}
