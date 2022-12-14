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
        IBuildSearchResult toReturn = new IBuildSearchResult();
        toReturn.setData(
                result.getContent().stream()
                        .map(BuildMapper::toApi)
                        .toList()
        );
        toReturn.setPage(result.getNumber());
        toReturn.setHasNext(result.hasNext());
        toReturn.setHasPrevious(result.hasPrevious());
        toReturn.setTotalElements(result.getTotalElements());
        toReturn.setTotalPages(result.getTotalPages());
        return toReturn;
    }

    public static IDeploymentSearchResult deploymentSearchResultToApi(Page<Deployment> result) {
        IDeploymentSearchResult toReturn = new IDeploymentSearchResult();
        toReturn.setData(
                result.getContent().stream()
                        .map(DeploymentMapper::toApi)
                        .toList()
        );
        toReturn.setPage(result.getNumber());
        toReturn.setHasNext(result.hasNext());
        toReturn.setHasPrevious(result.hasPrevious());
        toReturn.setTotalElements(result.getTotalElements());
        toReturn.setTotalPages(result.getTotalPages());
        return toReturn;
    }


}
