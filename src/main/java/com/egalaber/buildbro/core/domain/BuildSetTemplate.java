package com.egalaber.buildbro.core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(
        name = "build_set_template",
        indexes = {
                @Index(name = "unq_env_name", columnList = "name", unique = true)
        }
)
@Getter
@Setter
public class BuildSetTemplate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic(optional = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "build_set_template_id", nullable = false)
    private List<BuildTemplate> buildTemplates = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuildSetTemplate that = (BuildSetTemplate) o;
        return Objects.equals(getName(), that.getName()) && Objects.equals(getBuildTemplates(), that.getBuildTemplates());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getBuildTemplates());
    }

    @Override
    public String toString() {
        return "BuildSetTemplate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", buildTemplates=" + buildTemplates +
                '}';
    }
}
