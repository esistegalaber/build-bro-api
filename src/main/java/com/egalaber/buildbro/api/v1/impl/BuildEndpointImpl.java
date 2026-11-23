package com.egalaber.buildbro.api.v1.impl;

import com.egalaber.buildbro.api.fault.DataNotFoundException;
import com.egalaber.buildbro.api.model.IBuild;
import com.egalaber.buildbro.api.model.IBuildSearch;
import com.egalaber.buildbro.api.model.IBuildSearchResult;
import com.egalaber.buildbro.api.v1.BuildsApi;
import com.egalaber.buildbro.core.domain.Build;
import com.egalaber.buildbro.core.events.BuildCreatedEvent;
import com.egalaber.buildbro.core.service.BuildService;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.egalaber.buildbro.core.mapping.SearchResultMapper.buildSearchResultToApi;

/**
 * Implements {@link BuildsApi}, which is generated from the contract ({@code openapi/build-bro-api.yaml}).
 */
@RestController
@Transactional
public class BuildEndpointImpl implements BuildsApi {
    protected final ApplicationEventPublisher applicationEventPublisher;
    private final BuildService buildService;

    public BuildEndpointImpl(ApplicationEventPublisher applicationEventPublisher, BuildService buildService) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.buildService = buildService;
    }

    @Override
    public IBuildSearchResult searchBuilds(IBuildSearch search) {
        Page<Build> found = buildService.search(search);
        return buildSearchResultToApi(found);
    }

    @Override
    public IBuild getBuild(Long buildId) {
        return buildService.byId(buildId)
                .orElseThrow(() -> new DataNotFoundException("No Build found for buildId='" + buildId + "'"));
    }

    @Override
    public IBuild createBuild(IBuild build) {
        IBuild created = buildService.create(build);
        applicationEventPublisher.publishEvent(new BuildCreatedEvent(build));
        return created;
    }

    @Override
    public IBuild addBuildLabels(Long buildId, Map<String, String> buildLabels) {
        return buildService.addLabels(buildId, buildLabels);
    }
}
