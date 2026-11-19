package com.egalaber.buildbro.core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "branch")
@Getter
@Setter
public class Branch implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic(optional = false)
    @Column(name = "name", nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false, foreignKey = @ForeignKey(name = "fk_project_id", value = ConstraintMode.CONSTRAINT))
    private Project project;
    @Basic(optional = false)
    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Branch branch = (Branch) o;
        return Objects.equals(getName(), branch.getName()) && Objects.equals(getProject(), branch.getProject()) && Objects.equals(getActive(), branch.getActive());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getProject(), getActive());
    }

    @Override
    public String toString() {
        return "Branch{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", project=" + project +
                ", active=" + active +
                '}';
    }
}
