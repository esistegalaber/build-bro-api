package com.egalaber.buildbro.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.Map;

/**
 * Build search request. {@code minBuildNumber} and {@code maxBuildNumber} are exclusive bounds.
 */
@Schema(description = "Build search criteria. All fields are optional.")
public record IBuildSearch(
        @Schema(description = "Results per page", defaultValue = "10", example = "10") Integer pageSize,
        @Schema(description = "Zero-based page", defaultValue = "0", example = "0") Integer page,
        @Schema(description = "Build attribute to sort by", defaultValue = "buildNumber", example = "buildNumber") String sortAttribute,
        @Schema(description = "Sort direction", allowableValues = {"ASC", "DESC"}, defaultValue = "DESC") String sortDirection,
        @Schema(description = "Only builds of this project", example = "backend") String project,
        @Schema(description = "Only builds of this branch", example = "main") String branch,
        @Schema(description = "Only build numbers greater than this (exclusive)", example = "10") Long minBuildNumber,
        @Schema(description = "Only build numbers less than this (exclusive)", example = "20") Long maxBuildNumber,
        @Schema(description = "Builds carrying any of these key/value labels", example = "{\"qa\": \"passed\"}") Map<String, String> labels
) implements PagedSearch, Serializable {

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
