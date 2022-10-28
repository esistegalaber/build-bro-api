package com.egalaber.buildbro.api.impl;

import com.egalaber.buildbro.api.BuildEndpoint;
import com.egalaber.buildbro.api.fault.DataNotFoundException;
import com.egalaber.buildbro.api.model.IBuildSearch;
import com.egalaber.buildbro.api.model.IBuildSearchResult;
import com.egalaber.buildbro.api.model.IBuild;
import com.egalaber.buildbro.core.domain.Build;
import com.egalaber.buildbro.core.events.BuildCreatedEvent;
import com.egalaber.buildbro.core.service.BuildService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.Map;

import static com.egalaber.buildbro.api.impl.SearchResultMapper.mapToBuild;
import static com.egalaber.buildbro.api.impl.SearchResultMapper.mapToBuildSearchResult;

@RestController
@Transactional
public class BuildEndpointImpl implements BuildEndpoint {
    protected final ApplicationEventPublisher applicationEventPublisher;
    private final BuildService buildService;

    public BuildEndpointImpl(ApplicationEventPublisher applicationEventPublisher, BuildService buildService) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.buildService = buildService;
    }

    @Override
    public IBuildSearchResult search(IBuildSearch search) {
        Page<Build> found = buildService.search(search);
        return mapToBuildSearchResult(found);
    }

    @Override
    public IBuild get(Long buildId) throws DataNotFoundException {
        return buildService.byId(buildId)
                .map(SearchResultMapper::mapToBuild)
                .orElseThrow(() -> new DataNotFoundException("No Build found for buildId='" + buildId + "'"));
    }

    @Override
    public IBuild create(IBuild build) {
        String projectName = build.getProject();
        String branchName = build.getBranch();
        IBuild iBuild = mapToBuild(
                buildService.create(projectName, branchName, build.getBuildNumber())
        );
        applicationEventPublisher.publishEvent(new BuildCreatedEvent(projectName, branchName));
        return iBuild;
    }

    @Override
    public IBuild addLabels(Long buildId, Map<String, String> buildLabels) throws DataNotFoundException {
        return mapToBuild(
                buildService.addLabels(buildId, buildLabels)
        );
    }
}
