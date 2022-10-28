package com.egalaber.buildbro.core.domain;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(
        name = "build",
        indexes = {
                @Index(name = "unq_idx_project_branch", columnList = "project,branch,build_number")
        }
)
public class Build {
    public Build() {
    }

    public Build(String project, String branch, Long buildNumber) {
        this.project = project;
        this.branch = branch;
        this.buildNumber = buildNumber;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic(optional = false)

    @Column(name = "project")
    private String project;

    @Basic(optional = false)
    @Column(name = "branch")
    private String branch;

    @Basic(optional = false)
    @Column(name = "build_number")
    private Long buildNumber;

    @OneToMany(mappedBy = "build", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<BuildLabel> labels = new TreeSet<>();

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

    public Long getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(Long buildNumber) {
        this.buildNumber = buildNumber;
    }

    public Set<BuildLabel> getLabels() {
        return labels;
    }

    public void setLabels(Set<BuildLabel> labels) {
        this.labels = labels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Build build = (Build) o;
        return Objects.equals(getProject(), build.getProject()) && Objects.equals(getBranch(), build.getBranch()) && Objects.equals(getBuildNumber(), build.getBuildNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProject(), getBranch(), getBuildNumber());
    }

    @Override
    public String toString() {
        return "Build{" +
                "id=" + id +
                ", project='" + project + '\'' +
                ", branch='" + branch + '\'' +
                ", buildNumber=" + buildNumber +
                '}';
    }
}
