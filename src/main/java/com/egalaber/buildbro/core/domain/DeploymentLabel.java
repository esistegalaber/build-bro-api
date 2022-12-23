package com.egalaber.buildbro.core.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(
        name = "deployment_label",
        indexes = {
                @Index(name = "idx_deployment_label_value", columnList = "label_key")
        }
)
public class DeploymentLabel implements Serializable, Comparable<DeploymentLabel> {
    public DeploymentLabel() {
    }

    public DeploymentLabel(Deployment deployment, String key, String value) {
        this.deployment = deployment;
        this.key = key;
        this.value = value;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Deployment.class, optional = false)
    @JoinColumn(name = "deployment_id", nullable = false, foreignKey = @ForeignKey(name = "fk_deployment", value = ConstraintMode.CONSTRAINT))
    @JsonBackReference
    private Deployment deployment;

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

    public Deployment getDeployment() {
        return deployment;
    }

    public void setDeployment(Deployment deployment) {
        this.deployment = deployment;
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
    public int compareTo(DeploymentLabel other) {
        return getKey().compareTo(other.getKey());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeploymentLabel that = (DeploymentLabel) o;
        return Objects.equals(getDeployment(), that.getDeployment()) && Objects.equals(getKey(), that.getKey()) && Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDeployment(), getKey(), getValue());
    }

    @Override
    public String toString() {
        return "DeploymentLabel{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
