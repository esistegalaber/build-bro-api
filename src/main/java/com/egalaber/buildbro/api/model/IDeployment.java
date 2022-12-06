package com.egalaber.buildbro.api.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IDeployment implements Serializable {
    private Long id;
    private String serverName;
    private LocalDateTime deployedAt;
    private List<IBuild> builds = new ArrayList<>();
    private List<IDeploymentLabel> labels = new ArrayList<>();

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

    public List<IDeploymentLabel> getLabels() {
        return labels;
    }

    public void setLabels(List<IDeploymentLabel> labels) {
        this.labels = labels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IDeployment that = (IDeployment) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getServerName(), that.getServerName()) && Objects.equals(getDeployedAt(), that.getDeployedAt()) && Objects.equals(getBuilds(), that.getBuilds()) && Objects.equals(getLabels(), that.getLabels());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getServerName(), getDeployedAt(), getBuilds(), getLabels());
    }

    @Override
    public String toString() {
        return "IDeployment{" +
                "id=" + id +
                ", serverName='" + serverName + '\'' +
                ", deployedAt=" + deployedAt +
                '}';
    }
}
