package com.egalaber.buildbro.core.repository;

import com.egalaber.buildbro.core.domain.Deployment;
import com.egalaber.buildbro.core.domain.Deployment_;
import com.egalaber.buildbro.core.domain.Server_;
import org.springframework.data.jpa.domain.Specification;

public class DeploymentSpecs {
    private DeploymentSpecs() {
    }

    public static Specification<Deployment> allDeployments() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
    }

    public static Specification<Deployment> onServer(String serverName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Deployment_.server).get(Server_.NAME), serverName);
    }

}
