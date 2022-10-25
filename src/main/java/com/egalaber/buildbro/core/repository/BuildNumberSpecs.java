package com.egalaber.buildbro.core.repository;

import com.egalaber.buildbro.core.domain.BuildNumber;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

public class BuildNumberSpecs {
    private BuildNumberSpecs() {
    }

    public static Specification<BuildNumber> buildNumberOf(@NonNull String project, @NonNull String branch) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("project"), project),
                criteriaBuilder.equal(root.get("branch"), branch)
        );
    }
}
