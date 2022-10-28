package com.egalaber.buildbro.core.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "build_set_template",
        indexes = {
                @Index(name = "unq_env_name", columnList = "name", unique = true)
        }
)
public class BuildSetTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic(optional = false)
    private String name;

    @OneToMany(mappedBy = "buildSetTemplate", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<BuildTemplate> buildTemplates = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BuildTemplate> getBuildTemplates() {
        return buildTemplates;
    }

    public void setBuildTemplates(List<BuildTemplate> buildTemplates) {
        this.buildTemplates = buildTemplates;
    }
}
