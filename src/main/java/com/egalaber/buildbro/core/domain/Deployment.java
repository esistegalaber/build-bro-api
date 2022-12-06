package com.egalaber.buildbro.core.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name = "deployment")
public class Deployment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic(optional = false)
    @Column(name = "deployed_at")
    private LocalDateTime deployedAt;

    @OneToMany(targetEntity = Build.class, fetch = FetchType.EAGER)
    @JoinTable(name = "deployed_builds",
            joinColumns = @JoinColumn(name = "deployment_id"),
            inverseJoinColumns = @JoinColumn(name = "build_Id")
    )
    private Set<Build> builds = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "server_id")
    private Server server;

    @OneToMany(mappedBy = "deployment", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<DeploymentLabel> labels = new TreeSet<>();

    @PrePersist
    public void prePersist() {
        this.deployedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDeployedAt() {
        return deployedAt;
    }

    public void setDeployedAt(LocalDateTime deployedAt) {
        this.deployedAt = deployedAt;
    }

    public Set<Build> getBuilds() {
        return builds;
    }

    public void setBuilds(Set<Build> builds) {
        this.builds = builds;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Set<DeploymentLabel> getLabels() {
        return labels;
    }

    public void setLabels(Set<DeploymentLabel> labels) {
        this.labels = labels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deployment that = (Deployment) o;
        return Objects.equals(getDeployedAt(), that.getDeployedAt()) && Objects.equals(getServer(), that.getServer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDeployedAt(), getServer());
    }
}
