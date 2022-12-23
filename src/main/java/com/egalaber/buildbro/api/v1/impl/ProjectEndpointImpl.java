package com.egalaber.buildbro.api.v1.impl;

import com.egalaber.buildbro.api.fault.DataNotFoundException;
import com.egalaber.buildbro.api.model.IBranch;
import com.egalaber.buildbro.api.model.IProject;
import com.egalaber.buildbro.api.v1.ProjectEndpoint;
import com.egalaber.buildbro.core.service.ProjectService;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Transactional
public class ProjectEndpointImpl implements ProjectEndpoint {
    private final ProjectService projectService;

    public ProjectEndpointImpl(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    public List<IProject> getProjectData() {
        return projectService.projectData(true);
    }

    @Override
    public IProject projectActive(Long projectId) throws DataNotFoundException {
        return projectService.setProjectActive(projectId, true);
    }

    @Override
    public IProject projectInactive(Long projectId) throws DataNotFoundException {
        return projectService.setProjectActive(projectId, false);
    }

    @Override
    public IBranch branchActive(Long branchId) throws DataNotFoundException {
        return projectService.setBranchActive(branchId, true);
    }

    @Override
    public IBranch branchInactive(Long branchId) throws DataNotFoundException {
        return projectService.setBranchActive(branchId, false);
    }
}
