package com.egalaber.buildbro.core.repository;

import com.egalaber.buildbro.core.domain.BuildLabel;
import org.springframework.data.jpa.domain.Specification;

public class BuildLabelSpecs {
    private static final String KEY = "key";
    private static final String VALUE = "value";

    private BuildLabelSpecs() {
    }

    public static Specification<BuildLabel> labelsWithKeyAndValue(String key, String value) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get(KEY), key),
                criteriaBuilder.equal(root.get(VALUE), value)
        );
    }
}
