package com.egalaber.buildbro.core.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

@Entity
@Table(name = "build_template")
public class BuildTemplate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic(optional = false)
    @Column(name = "project")
    private String project;
    @Basic(optional = false)
    @Column(name = "branch")
    private String branch;
    @Basic
    @Column(name = "build_number")
    private Long buildNumber;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "build_template_labels", joinColumns = @JoinColumn(name = "build_template_id"))
    private Map<String, String> labels = new TreeMap<>();

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

    public Map<String, String> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuildTemplate that = (BuildTemplate) o;
        return Objects.equals(getProject(), that.getProject()) && Objects.equals(getBranch(), that.getBranch()) && Objects.equals(getBuildNumber(), that.getBuildNumber()) && Objects.equals(getLabels(), that.getLabels());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProject(), getBranch(), getBuildNumber(), getLabels());
    }
}
