package com.egalaber.buildbro.core.repository;

import com.egalaber.buildbro.core.domain.Project;
import org.springframework.data.jpa.domain.Specification;

public class ProjectSpecs {
    private ProjectSpecs() {
    }

    public static Specification<Project> projectNamed(String projectName) {
        return (root, query, criteriaBuilder) -> (
                criteriaBuilder.equal(root.get("name"), projectName)
        );
    }
}
