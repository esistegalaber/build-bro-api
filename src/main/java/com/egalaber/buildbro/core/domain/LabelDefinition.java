package com.egalaber.buildbro.core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * The definition of a label key (issue #7). Type and styles are stored by the names of the contract's
 * {@code LabelType} and {@code LabelStyle} enums.
 */
@Entity
@Table(
        name = "label_definition",
        indexes = {
                @Index(name = "unq_label_definition_key", columnList = "label_key", unique = true)
        }
)
@Getter
@Setter
public class LabelDefinition implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "label_key", nullable = false)
    private String key;
    @Basic
    @Column(name = "display_name")
    private String displayName;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "applies_to_build", nullable = false)
    private Boolean appliesToBuild = true;
    @Basic(optional = false)
    @Column(name = "applies_to_deployment", nullable = false)
    private Boolean appliesToDeployment = true;
    @Basic(optional = false)
    @Column(name = "label_type", nullable = false)
    private String type = "STRING";
    @Basic
    @Column(name = "pattern")
    private String pattern;
    @Basic
    @Column(name = "style")
    private String style;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "label_definition_allowed_value", joinColumns = @JoinColumn(name = "label_definition_id"))
    @OrderColumn(name = "position")
    @Column(name = "label_value", nullable = false)
    private List<String> allowedValues = new ArrayList<>();
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "label_definition_value_style", joinColumns = @JoinColumn(name = "label_definition_id"))
    @MapKeyColumn(name = "label_value")
    @Column(name = "style", nullable = false)
    private Map<String, String> valueStyles = new TreeMap<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LabelDefinition that = (LabelDefinition) o;
        return Objects.equals(getKey(), that.getKey()) && Objects.equals(getDisplayName(), that.getDisplayName()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getAppliesToBuild(), that.getAppliesToBuild()) && Objects.equals(getAppliesToDeployment(), that.getAppliesToDeployment()) && Objects.equals(getType(), that.getType()) && Objects.equals(getPattern(), that.getPattern()) && Objects.equals(getStyle(), that.getStyle()) && Objects.equals(getAllowedValues(), that.getAllowedValues()) && Objects.equals(getValueStyles(), that.getValueStyles());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey(), getDisplayName(), getDescription(), getAppliesToBuild(), getAppliesToDeployment(), getType(), getPattern(), getStyle(), getAllowedValues(), getValueStyles());
    }

    @Override
    public String toString() {
        return "LabelDefinition{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
