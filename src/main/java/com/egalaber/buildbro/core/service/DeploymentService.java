package com.egalaber.buildbro.core.service;

import com.egalaber.buildbro.api.fault.DataNotFoundException;
import com.egalaber.buildbro.api.model.*;
import com.egalaber.buildbro.core.domain.BuildLabel;
import com.egalaber.buildbro.core.domain.Deployment;
import com.egalaber.buildbro.core.domain.DeploymentLabel;
import com.egalaber.buildbro.core.domain.Server;
import com.egalaber.buildbro.core.mapping.DeploymentMapper;
import com.egalaber.buildbro.core.mapping.SearchResultMapper;
import com.egalaber.buildbro.core.repository.BuildRepository;
import com.egalaber.buildbro.core.repository.DeploymentRepository;
import com.egalaber.buildbro.core.repository.DeploymentSpecs;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@Transactional
@Slf4j
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
        Server theServer = serverService.findOrCreate(deployment.serverName());
        Deployment toCreate = new Deployment();
        toCreate.setServer(theServer);
        List<Long> buildIds = deployment.builds().stream().map(IBuild::id).toList();
        buildRepository.findAllById(buildIds)
                .forEach(toCreate.getBuilds()::add);
        deployment.labels().forEach(
                newLabel -> toCreate.getLabels().add(new DeploymentLabel(toCreate, newLabel.key(), newLabel.value()))
        );
        return DeploymentMapper.toApi(deploymentRepository.save(toCreate));
    }

    public IDeployment current(String serverName) throws DataNotFoundException {
        return search(latestOn(serverName)).data()
                .stream()
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException("No Deployment found for serverName='" + serverName + "'"));
    }

    public IDeploymentSearchResult search(IDeploymentSearch search) {
        return SearchResultMapper.deploymentSearchResultToApi(
                deploymentRepository.findAll(toSpecification(search), Paging.of(search))
        );
    }

    /** The latest deployment on one server: first page of one, default sorting (newest first). */
    private static IDeploymentSearch latestOn(String serverName) {
        return new IDeploymentSearch(1, 0, null, null, serverName);
    }

    private Specification<Deployment> toSpecification(IDeploymentSearch search) {
        Specification<Deployment> theSpec = DeploymentSpecs.allDeployments();
        if (!ObjectUtils.isEmpty(search.serverName())) {
            return DeploymentSpecs.onServer(search.serverName());
        }
        return theSpec;
    }

    public IDeployment addLabelsToBuilds(Long deploymentId, List<IBuildLabel> labels) throws DataNotFoundException {
        Deployment deployment = deploymentRepository.findById(deploymentId)
                .orElseThrow(() -> new DataNotFoundException("No Deployment found with id='" + deploymentId + "'"));
        deployment.getBuilds().forEach(build ->
                labels.forEach(label -> {
                    build.getLabels().add(new BuildLabel(build, label.key(), label.value()));
                    buildRepository.save(build);
                })
        );
        return deploymentRepository.findById(deploymentId)
                .map(DeploymentMapper::toApi)
                .orElseThrow(() -> new DataNotFoundException("No Deployment found with id='" + deploymentId + "'"));
    }
}
