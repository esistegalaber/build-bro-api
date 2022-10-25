package com.egalaber.buildbro.core.repository;

import com.egalaber.buildbro.core.domain.BuildLabel;
import org.springframework.data.jpa.domain.Specification;

public class BuildLabelSpecs {
    private BuildLabelSpecs() {
    }

    public static Specification<BuildLabel> labelsWithKeyAndValue(String key, String value) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("key"), key),
                criteriaBuilder.equal(root.get("value"), value)
        );
    }
}
