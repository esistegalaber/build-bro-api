package com.egalaber.buildbro.core.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "build_number", indexes = {
        @Index(name = "unq_idx_project_branch", columnList = "project,branch", unique = true)
})
public class BuildNumber {

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long counter) {
        this.number = counter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuildNumber that = (BuildNumber) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getProject(), that.getProject()) && Objects.equals(getBranch(), that.getBranch()) && Objects.equals(getNumber(), that.getNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getProject(), getBranch(), getNumber());
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
