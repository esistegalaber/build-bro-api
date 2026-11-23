package com.egalaber.buildbro.core.repository;

import com.egalaber.buildbro.core.domain.LabelDefinition;
import com.egalaber.buildbro.core.domain.LabelDefinition_;
import org.springframework.data.jpa.domain.Specification;

import java.util.Locale;

public class LabelDefinitionSpecs {
    private LabelDefinitionSpecs() {
    }

    /** The definition of {@code key}, ignoring case. */
    public static Specification<LabelDefinition> byKey(String key) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                criteriaBuilder.lower(root.get(LabelDefinition_.key)), key.toLowerCase(Locale.ROOT));
    }
}
