package com.egalaber.buildbro.core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "project")
@Getter
@Setter
public class Project implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic(optional = false)
    private String name;

    @OneToMany(
            mappedBy = "project",
            cascade = CascadeType.ALL, fetch = FetchType.EAGER
    )
    private List<Branch> branches = new ArrayList<>();

    @Basic(optional = false)
    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(getActive(), project.getActive()) && Objects.equals(getName(), project.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getActive(), getName());
    }

    @Override
    public String toString() {
        return "Project{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                ", active=" + active +
                '}';
    }
}
