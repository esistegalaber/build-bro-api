package com.egalaber.buildbro.api.impl;

import com.egalaber.buildbro.api.BuildNumberEndpoint;
import com.egalaber.buildbro.api.model.IBuildNumber;
import com.egalaber.buildbro.api.model.ProjectBranch;
import com.egalaber.buildbro.core.service.BuildNumberService;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@Transactional
public class BuildNumberEndpointImpl implements BuildNumberEndpoint {
    private final BuildNumberService buildNumberService;

    public BuildNumberEndpointImpl(BuildNumberService buildNumberService) {
        this.buildNumberService = buildNumberService;
    }

    @Override
    public IBuildNumber next(ProjectBranch projectBranch) {
        return BuildNumberMapper.from(
                buildNumberService.next(projectBranch.getProject(), projectBranch.getBranch())
        );
    }

    @Override
    public IBuildNumber current(String project, String branch) {
        return BuildNumberMapper.from(
                buildNumberService.current(project, branch)
        );
    }

    @Override
    public IBuildNumber set(IBuildNumber buildNumber) {
        return BuildNumberMapper.from(
                buildNumberService.set(buildNumber.getProject(), buildNumber.getBranch(), buildNumber.getNumber())
        );
    }
}
