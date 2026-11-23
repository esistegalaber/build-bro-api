package com.egalaber.buildbro.api.v1.impl;

import com.egalaber.buildbro.api.model.IBranchedProject;
import com.egalaber.buildbro.api.model.IBuildNumber;
import com.egalaber.buildbro.api.v1.BuildNumbersApi;
import com.egalaber.buildbro.core.service.BuildNumberService;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implements {@link BuildNumbersApi}, which is generated from the contract ({@code openapi/build-bro-api.yaml}).
 */
@RestController
@Transactional
public class BuildNumberEndpointImpl implements BuildNumbersApi {
    private final BuildNumberService buildNumberService;

    public BuildNumberEndpointImpl(BuildNumberService buildNumberService) {
        this.buildNumberService = buildNumberService;
    }

    @Override
    public IBuildNumber nextBuildNumber(IBranchedProject branchedProject) {
        return buildNumberService.next(branchedProject.project(), branchedProject.branch());
    }

    @Override
    public IBuildNumber currentBuildNumber(String project, String branch) {
        return buildNumberService.current(project, branch);
    }

    @Override
    public IBuildNumber setBuildNumber(IBuildNumber buildNumber) {
        return buildNumberService.set(buildNumber.project(), buildNumber.branch(), buildNumber.number());
    }
}
