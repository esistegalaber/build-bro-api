package com.egalaber.buildbro.api.v1.impl;

import com.egalaber.buildbro.api.fault.DataNotFoundException;
import com.egalaber.buildbro.api.model.*;
import com.egalaber.buildbro.api.v1.DeploymentEndpoint;
import com.egalaber.buildbro.core.service.DeploymentService;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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
    public IDeployment create(IDeployment deployment) {
        return deploymentService.create(deployment);
    }

    @Override
    public IDeployment current(String serverName) throws DataNotFoundException {
        return deploymentService.current(serverName);
    }

    @Override
    public IDeployment addLabelsToBuilds(Long deploymentId, List<IBuildLabel> labels) throws DataNotFoundException {
        return deploymentService.addLabelsToBuilds(deploymentId, labels);
    }
}
