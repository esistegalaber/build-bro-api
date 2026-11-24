package com.egalaber.buildbro.core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name = "deployment")
@Getter
@Setter
public class Deployment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic(optional = false)
    @Column(name = "deployed_at")
    private Instant deployedAt;

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
        this.deployedAt = Instant.now();
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
