package com.egalaber.buildbro.core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "build_number", indexes = {
        @Index(name = "unq_idx_project_branch", columnList = "project,branch", unique = true)
})
@Getter
@Setter
public class BuildNumber implements Serializable {

    public BuildNumber() {
    }

    public BuildNumber(String project, String branch) {
        this.project = project;
        this.branch = branch;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project")
    private String project;

    @Column(name = "branch")
    private String branch;

    @Column(name = "number")
    private Long number = 0L;

    @Transient
    public void increment() {
        this.number++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuildNumber that = (BuildNumber) o;
        return Objects.equals(getProject(), that.getProject()) && Objects.equals(getBranch(), that.getBranch()) && Objects.equals(getNumber(), that.getNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProject(), getBranch(), getNumber());
    }

    @Override
    public String toString() {
        return "BuildNumber{" +
                "id=" + id +
                ", project='" + project + '\'' +
                ", branch='" + branch + '\'' +
                ", counter=" + number +
                '}';
    }
}
