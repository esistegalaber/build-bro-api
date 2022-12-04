package com.egalaber.buildbro.core.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "deployment")
public class Deployment {
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
}
