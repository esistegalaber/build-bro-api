package com.egalaber.buildbro.core.repository;

import com.egalaber.buildbro.core.domain.BuildSetTemplate;
import org.springframework.data.jpa.domain.Specification;

public class BuildSetTemplateSpecs {
    private BuildSetTemplateSpecs() {
    }

    public static Specification<BuildSetTemplate> byName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), name);
    }
}
