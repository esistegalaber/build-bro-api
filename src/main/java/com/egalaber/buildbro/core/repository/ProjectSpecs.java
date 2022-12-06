package com.egalaber.buildbro.core.repository;

import com.egalaber.buildbro.core.domain.Project;
import com.egalaber.buildbro.core.domain.Project_;
import org.springframework.data.jpa.domain.Specification;

public class ProjectSpecs {
    private ProjectSpecs() {
    }

    public static Specification<Project> projectNamed(String projectName) {
        return (root, query, criteriaBuilder) -> (
                criteriaBuilder.equal(root.get(Project_.NAME), projectName)
        );
    }

    public static Specification<Project> allProjects(boolean includeInactive) {
        return (root, query, criteriaBuilder) -> includeInactive
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.equal(root.get(Project_.ACTIVE), true);
    }
}
