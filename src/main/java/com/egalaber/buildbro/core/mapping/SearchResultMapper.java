package com.egalaber.buildbro.core.mapping;

import com.egalaber.buildbro.api.model.IBuildSearchResult;
import com.egalaber.buildbro.api.model.IDeploymentSearchResult;
import com.egalaber.buildbro.core.domain.Build;
import com.egalaber.buildbro.core.domain.Deployment;
import org.springframework.data.domain.Page;

public class SearchResultMapper {
    private SearchResultMapper() {
    }

    public static IBuildSearchResult buildSearchResultToApi(Page<Build> result) {
        return new IBuildSearchResult(
                result.getContent().stream().map(BuildMapper::toApi).toList(),
                result.getNumber(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.hasNext(),
                result.hasPrevious()
        );
    }

    public static IDeploymentSearchResult deploymentSearchResultToApi(Page<Deployment> result) {
        return new IDeploymentSearchResult(
                result.getContent().stream().map(DeploymentMapper::toApi).toList(),
                result.getNumber(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.hasNext(),
                result.hasPrevious()
        );
    }


}
