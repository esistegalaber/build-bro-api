package com.egalaber.buildbro.core.service;

import com.egalaber.buildbro.api.model.IDeployment;
import com.egalaber.buildbro.api.model.IDeploymentSearch;
import com.egalaber.buildbro.core.domain.Deployment;
import com.egalaber.buildbro.core.domain.Server;
import com.egalaber.buildbro.core.mapping.DeploymentMapper;
import com.egalaber.buildbro.core.repository.BuildRepository;
import com.egalaber.buildbro.core.repository.DeploymentRepository;
import com.egalaber.buildbro.core.repository.DeploymentSpecs;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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

    public Page<Deployment> search(IDeploymentSearch search) {
        return deploymentRepository.findAll(toSpecification(search), search.page());
    }

    private Specification<Deployment> toSpecification(IDeploymentSearch search) {
        Specification<Deployment> theSpec = DeploymentSpecs.allDeployments();
        if (!ObjectUtils.isEmpty(search.getServerName())) {
            theSpec = theSpec.and(DeploymentSpecs.onServer(search.getServerName()));
        }
        return theSpec;
    }
}
