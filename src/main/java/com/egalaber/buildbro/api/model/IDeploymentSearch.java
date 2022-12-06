package com.egalaber.buildbro.api.model;

public class IDeploymentSearch extends BaseSearch {
    private static final String DEFAULT_SORT_ATTRIBUTE = "deployedAt";
    private String serverName;

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    protected String defaultSortAttribute() {
        return DEFAULT_SORT_ATTRIBUTE;
    }
}
