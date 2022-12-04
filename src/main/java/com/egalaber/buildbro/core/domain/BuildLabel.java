package com.egalaber.buildbro.core.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(
        name = "build_label",
        indexes = {
                @Index(name = "idx_build_label_value", columnList = "label_key")
        }
)
public class BuildLabel implements Serializable, Comparable<BuildLabel> {
    public BuildLabel() {
    }

    public BuildLabel(Build build, String key, String value) {
        this.build = build;
        this.key = key;
        this.value = value;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Build.class, optional = false)
    @JoinColumn(name = "build_id", nullable = false, foreignKey = @ForeignKey(name = "fk_build", value = ConstraintMode.CONSTRAINT))
    @JsonBackReference
    private Build build;

    @Basic(optional = false)
    @Column(name = "label_key", nullable = false)
    private String key;

    @Basic(optional = false)
    @Column(name = "label_value", nullable = false)
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Build getBuild() {
        return build;
    }

    public void setBuild(Build build) {
        this.build = build;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int compareTo(BuildLabel other) {
        return getKey().compareTo(other.getKey());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuildLabel that = (BuildLabel) o;
        return Objects.equals(getBuild(), that.getBuild()) && Objects.equals(getKey(), that.getKey()) && Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBuild(), getKey(), getValue());
    }

    @Override
    public String toString() {
        return "BuildLabel{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
