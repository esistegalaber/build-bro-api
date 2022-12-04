package com.egalaber.buildbro.api.v1.impl;

import com.egalaber.buildbro.api.fault.DataNotFoundException;
import com.egalaber.buildbro.api.fault.InvalidRequestException;
import com.egalaber.buildbro.api.model.IBuild;
import com.egalaber.buildbro.api.model.IBuildSearch;
import com.egalaber.buildbro.api.model.IBuildSearchResult;
import com.egalaber.buildbro.api.v1.BuildEndpoint;
import com.egalaber.buildbro.core.domain.Build;
import com.egalaber.buildbro.core.events.BuildCreatedEvent;
import com.egalaber.buildbro.core.service.BuildService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.Map;

import static com.egalaber.buildbro.core.mapping.SearchResultMapper.toApi;

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
        return toApi(found);
    }

    @Override
    public IBuild get(Long buildId) throws DataNotFoundException {
        return buildService.byId(buildId)
                .orElseThrow(() -> new DataNotFoundException("No Build found for buildId='" + buildId + "'"));
    }

    @Override
    public IBuild create(IBuild build) throws InvalidRequestException {
        IBuild created = buildService.create(build);
        applicationEventPublisher.publishEvent(new BuildCreatedEvent(build));
        return created;
    }

    @Override
    public IBuild addLabels(Long buildId, Map<String, String> buildLabels) throws DataNotFoundException {
        return buildService.addLabels(buildId, buildLabels);
    }
}
