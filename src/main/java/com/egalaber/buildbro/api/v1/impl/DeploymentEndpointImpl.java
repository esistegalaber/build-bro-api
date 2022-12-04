package com.egalaber.buildbro.api.v1.impl;

import com.egalaber.buildbro.api.model.IDeployment;
import com.egalaber.buildbro.api.v1.DeploymentEndpoint;
import com.egalaber.buildbro.core.service.DeploymentService;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@Transactional
public class DeploymentEndpointImpl implements DeploymentEndpoint {
    private final DeploymentService deploymentService;

    public DeploymentEndpointImpl(DeploymentService deploymentService) {
        this.deploymentService = deploymentService;
    }

    @Override
    public IDeployment add(String server, List<Long> buildIds) {
        return deploymentService.create(server, buildIds);
    }
}
