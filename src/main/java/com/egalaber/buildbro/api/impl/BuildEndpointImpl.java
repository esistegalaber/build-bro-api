package com.egalaber.buildbro.api.impl;

import com.egalaber.buildbro.api.BuildEndpoint;
import com.egalaber.buildbro.api.fault.DataNotFoundException;
import com.egalaber.buildbro.api.model.BuildSearch;
import com.egalaber.buildbro.api.model.BuildSearchResult;
import com.egalaber.buildbro.api.model.IBuild;
import com.egalaber.buildbro.core.domain.Build;
import com.egalaber.buildbro.core.service.BuildService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.Map;

import static com.egalaber.buildbro.api.impl.SearchResultMapper.mapToBuild;
import static com.egalaber.buildbro.api.impl.SearchResultMapper.mapToBuildSearchResult;

@RestController
@Transactional
public class BuildEndpointImpl implements BuildEndpoint {
    private final BuildService buildService;

    public BuildEndpointImpl(BuildService buildService) {
        this.buildService = buildService;
    }

    @Override
    public BuildSearchResult search(BuildSearch search) {
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
        return mapToBuild(
                buildService.create(build.getProject(), build.getBranch(), build.getBuildNumber())
        );
    }

    @Override
    public IBuild addLabels(Long buildId, Map<String, String> buildLabels) throws DataNotFoundException {
        return mapToBuild(
                buildService.addLabels(buildId, buildLabels)
        );
    }
}
