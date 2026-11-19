package com.egalaber.buildbro.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

@Schema(description = "Deployment search criteria. All fields are optional.")
public record IDeploymentSearch(
        @Schema(description = "Results per page", defaultValue = "10", example = "10") Integer pageSize,
        @Schema(description = "Zero-based page", defaultValue = "0", example = "0") Integer page,
        @Schema(description = "Deployment attribute to sort by", defaultValue = "deployedAt", example = "deployedAt") String sortAttribute,
        @Schema(description = "Sort direction", allowableValues = {"ASC", "DESC"}, defaultValue = "DESC") String sortDirection,
        @Schema(description = "Only deployments to this server", example = "staging-01") String serverName
) implements PagedSearch, Serializable {

    private static final String DEFAULT_SORT_ATTRIBUTE = "deployedAt";

    /** The latest deployment(s) on one server. */
    public static IDeploymentSearch forServer(String serverName, int pageSize) {
        return new IDeploymentSearch(pageSize, null, null, null, serverName);
    }

    @Override
    public String defaultSortAttribute() {
        return DEFAULT_SORT_ATTRIBUTE;
    }
}
