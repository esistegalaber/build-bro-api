package com.egalaber.buildbro.api.model;

import java.io.Serializable;

public record IDeploymentSearch(Integer pageSize, Integer page, String sortAttribute, String sortDirection,
                                String serverName) implements PagedSearch, Serializable {

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
