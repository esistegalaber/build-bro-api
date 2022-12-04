package com.egalaber.buildbro.core.repository;

import com.egalaber.buildbro.core.domain.Server;
import org.springframework.data.jpa.domain.Specification;

public class ServerSpecs {
    private ServerSpecs() {
    }

    public static Specification<Server> byName(String serverName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), serverName);
    }
}
