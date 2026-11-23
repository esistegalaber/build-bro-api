package com.egalaber.buildbro.api.v1.impl;

import com.egalaber.buildbro.api.model.IBuildLabel;
import com.egalaber.buildbro.api.model.IDeployment;
import com.egalaber.buildbro.api.model.IDeploymentSearch;
import com.egalaber.buildbro.api.model.IDeploymentSearchResult;
import com.egalaber.buildbro.api.v1.DeploymentsApi;
import com.egalaber.buildbro.core.service.DeploymentService;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Implements {@link DeploymentsApi}, which is generated from the contract ({@code openapi/build-bro-api.yaml}).
 */
@RestController
@Transactional
public class DeploymentEndpointImpl implements DeploymentsApi {
    private final DeploymentService deploymentService;

    public DeploymentEndpointImpl(DeploymentService deploymentService) {
        this.deploymentService = deploymentService;
    }

    @Override
    public IDeploymentSearchResult searchDeployments(IDeploymentSearch search) {
        return deploymentService.search(search);
    }

    @Override
    public IDeployment createDeployment(IDeployment deployment) {
        return deploymentService.create(deployment);
    }

    @Override
    public IDeployment currentDeployment(String serverName) {
        return deploymentService.current(serverName);
    }

    @Override
    public IDeployment addLabelsToDeployedBuilds(Long deploymentId, List<IBuildLabel> labels) {
        return deploymentService.addLabelsToBuilds(deploymentId, labels);
    }
}
