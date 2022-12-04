package com.egalaber.buildbro.api.v1.impl;

import com.egalaber.buildbro.api.v1.BuildNumberEndpoint;
import com.egalaber.buildbro.api.model.IBranchedProject;
import com.egalaber.buildbro.api.model.IBuildNumber;
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
    public IBuildNumber next(IBranchedProject branchedProject) {
        return buildNumberService.next(branchedProject.getProject(), branchedProject.getBranch());
    }

    @Override
    public IBuildNumber current(String project, String branch) {
        return buildNumberService.current(project, branch);
    }

    @Override
    public IBuildNumber set(IBuildNumber buildNumber) {
        return buildNumberService.set(buildNumber.getProject(), buildNumber.getBranch(), buildNumber.getNumber());
    }
}
