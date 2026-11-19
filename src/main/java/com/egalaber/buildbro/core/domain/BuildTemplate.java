package com.egalaber.buildbro.core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

@Entity
@Table(name = "build_template")
@Getter
@Setter
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
