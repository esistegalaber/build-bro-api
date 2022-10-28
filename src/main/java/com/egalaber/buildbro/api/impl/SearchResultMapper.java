package com.egalaber.buildbro.api.impl;

import com.egalaber.buildbro.api.model.IBuildSearchResult;
import com.egalaber.buildbro.api.model.IBuild;
import com.egalaber.buildbro.api.model.IBuildLabel;
import com.egalaber.buildbro.core.domain.Build;
import com.egalaber.buildbro.core.domain.BuildLabel;
import org.springframework.data.domain.Page;

import java.util.stream.Collectors;

public class SearchResultMapper {
    private SearchResultMapper() {
    }

    public static IBuildSearchResult mapToBuildSearchResult(Page<Build> result) {
        IBuildSearchResult toReturn = new IBuildSearchResult();
        toReturn.setData(
                result.getContent().stream()
                        .map(SearchResultMapper::mapToBuild)
                        .collect(Collectors.toList())
        );
        toReturn.setPage(result.getNumber());
        toReturn.setHasNext(result.hasNext());
        toReturn.setHasPrevious(result.hasPrevious());
        toReturn.setTotalElements(result.getTotalElements());
        toReturn.setTotalPages(result.getTotalPages());
        return toReturn;
    }

    public static IBuild mapToBuild(Build build) {
        IBuild toReturn = new IBuild();
        toReturn.setBranch(build.getBranch());
        toReturn.setProject(build.getProject());
        toReturn.setId(build.getId());
        toReturn.setBuildNumber(build.getBuildNumber());
        build.getLabels().forEach((singleLabel) ->
                toReturn.getLabels().add(mapToBuildLabel(singleLabel))
        );
        return toReturn;
    }

    public static IBuildLabel mapToBuildLabel(BuildLabel buildLabel) {
        IBuildLabel toReturn = new IBuildLabel();
        toReturn.setId(buildLabel.getId());
        toReturn.setKey(buildLabel.getKey());
        toReturn.setValue(buildLabel.getValue());
        return toReturn;
    }
}
