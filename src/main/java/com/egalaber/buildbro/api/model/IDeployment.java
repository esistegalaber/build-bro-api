package com.egalaber.buildbro.api.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class IDeployment implements Serializable {
    private Long id;
    private String serverName;
    private LocalDateTime deployedAt;
    private List<IBuild> builds = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public List<IBuild> getBuilds() {
        return builds;
    }

    public void setBuilds(List<IBuild> builds) {
        this.builds = builds;
    }

    public LocalDateTime getDeployedAt() {
        return deployedAt;
    }

    public void setDeployedAt(LocalDateTime deployedAt) {
        this.deployedAt = deployedAt;
    }
}
