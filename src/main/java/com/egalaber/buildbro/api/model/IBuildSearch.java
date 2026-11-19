package com.egalaber.buildbro.api.model;

import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.Map;

/**
 * Build search request. {@code minBuildNumber} and {@code maxBuildNumber} are exclusive bounds.
 */
public record IBuildSearch(Integer pageSize, Integer page, String sortAttribute, String sortDirection,
                           String project, String branch, Long minBuildNumber, Long maxBuildNumber,
                           Map<String, String> labels) implements PagedSearch, Serializable {

    private static final String DEFAULT_SORT_ATTRIBUTE = "buildNumber";

    public IBuildSearch {
        labels = ModelCollections.map(labels);
    }

    /**
     * Search for the build a template points at: the given build number if it has one, else the latest build.
     */
    public static IBuildSearch fromBuildTemplate(IBuildTemplate artifact) {
        Long minBuildNumber = null;
        Long maxBuildNumber = null;
        if (artifact.buildNumber() != null && artifact.buildNumber() > 0) {
            // The bounds are exclusive, so widen by one on each side to match exactly this build number
            minBuildNumber = artifact.buildNumber() - 1;
            maxBuildNumber = artifact.buildNumber() + 1;
        }
        return new IBuildSearch(1, 0, DEFAULT_SORT_ATTRIBUTE, Sort.Direction.DESC.name(),
                artifact.project(), artifact.branch(), minBuildNumber, maxBuildNumber, artifact.labels());
    }

    @Override
    public String defaultSortAttribute() {
        return DEFAULT_SORT_ATTRIBUTE;
    }
}
