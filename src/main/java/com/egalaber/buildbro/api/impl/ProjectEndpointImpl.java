package com.egalaber.buildbro.api.impl;

import com.egalaber.buildbro.api.ProjectEndpoint;
import com.egalaber.buildbro.api.model.IProject;
import com.egalaber.buildbro.core.service.ProjectService;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
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
}
