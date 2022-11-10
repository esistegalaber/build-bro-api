package com.egalaber.buildbro.core.domain;

import javax.persistence.*;
import java.util.Map;
import java.util.TreeMap;

@Entity
@Table(name = "build_template")
public class BuildTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic(optional = false)
    @Column(name = "project")
    private String project;

    @Basic(optional = false)
    @Column(name = "branch")
    private String branch;

    @Basic(optional = true)
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
}
