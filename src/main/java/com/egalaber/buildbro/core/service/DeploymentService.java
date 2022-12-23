package com.egalaber.buildbro.core.service;

import com.egalaber.buildbro.api.model.IBuild;
import com.egalaber.buildbro.api.model.IDeployment;
import com.egalaber.buildbro.api.model.IDeploymentSearch;
import com.egalaber.buildbro.api.model.IDeploymentSearchResult;
import com.egalaber.buildbro.core.domain.Deployment;
import com.egalaber.buildbro.core.domain.DeploymentLabel;
import com.egalaber.buildbro.core.domain.Server;
import com.egalaber.buildbro.core.mapping.DeploymentMapper;
import com.egalaber.buildbro.core.mapping.SearchResultMapper;
import com.egalaber.buildbro.core.repository.BuildRepository;
import com.egalaber.buildbro.core.repository.DeploymentRepository;
import com.egalaber.buildbro.core.repository.DeploymentSpecs;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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

    public IDeployment create(IDeployment deployment) {
        Server theServer = serverService.findOrCreate(deployment.getServerName());
        Deployment toCreate = new Deployment();
        toCreate.setServer(theServer);
        List<Long> buildIds = deployment.getBuilds().stream().map(IBuild::getId).toList();
        buildRepository.findAllById(buildIds)
                .forEach(toCreate.getBuilds()::add);
        deployment.getLabels().forEach(
                newLabel -> toCreate.getLabels().add(new DeploymentLabel(toCreate, newLabel.getKey(), newLabel.getValue()))
        );
        return DeploymentMapper.toApi(deploymentRepository.save(toCreate));
    }

    public IDeploymentSearchResult search(IDeploymentSearch search) {
        return SearchResultMapper.deploymentSearchResultToApi(
                deploymentRepository.findAll(toSpecification(search), search.page())
        );
    }

    private Specification<Deployment> toSpecification(IDeploymentSearch search) {
        Specification<Deployment> theSpec = DeploymentSpecs.allDeployments();
        if (!ObjectUtils.isEmpty(search.getServerName())) {
            return DeploymentSpecs.onServer(search.getServerName());
        }
        return theSpec;
    }
}
