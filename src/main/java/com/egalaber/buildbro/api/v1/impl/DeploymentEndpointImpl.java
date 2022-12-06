package com.egalaber.buildbro.api.v1.impl;

import com.egalaber.buildbro.api.model.IDeployment;
import com.egalaber.buildbro.api.model.IDeploymentSearch;
import com.egalaber.buildbro.api.model.IDeploymentSearchResult;
import com.egalaber.buildbro.api.v1.DeploymentEndpoint;
import com.egalaber.buildbro.core.service.DeploymentService;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@Transactional
public class DeploymentEndpointImpl implements DeploymentEndpoint {
    private final DeploymentService deploymentService;

    public DeploymentEndpointImpl(DeploymentService deploymentService) {
        this.deploymentService = deploymentService;
    }

    @Override
    public IDeploymentSearchResult search(IDeploymentSearch search) {
        return deploymentService.search(search);
    }

    @Override
    public IDeployment add(IDeployment deployment) {
        return deploymentService.create(deployment);
    }
}
