package com.egalaber.buildbro.core.service;

import com.egalaber.buildbro.api.model.IDeployment;
import com.egalaber.buildbro.core.domain.Deployment;
import com.egalaber.buildbro.core.domain.Server;
import com.egalaber.buildbro.core.mapping.DeploymentMapper;
import com.egalaber.buildbro.core.repository.BuildRepository;
import com.egalaber.buildbro.core.repository.DeploymentRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DeploymentService {
    private final DeploymentRepository deploymentRepository;
    private final ServerService serverService;
    private final BuildRepository buildRepository;

    public DeploymentService(DeploymentRepository deploymentRepository, ServerService serverService, BuildRepository buildRepository) {
        this.deploymentRepository = deploymentRepository;
        this.serverService = serverService;
        this.buildRepository = buildRepository;
    }

    public IDeployment create(String serverName, List<Long> buildIds) {
        Server theServer = serverService.findOrCreate(serverName);
        Deployment toCreate = new Deployment();
        toCreate.setServer(theServer);
        buildRepository.findAllById(buildIds)
                .forEach(toCreate.getBuilds()::add);
        return DeploymentMapper.toApi(deploymentRepository.save(toCreate));
    }
}
