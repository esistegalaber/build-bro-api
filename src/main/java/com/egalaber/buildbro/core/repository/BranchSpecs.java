package com.egalaber.buildbro.core.repository;

import com.egalaber.buildbro.core.domain.Branch;
import com.egalaber.buildbro.core.domain.Project;
import org.springframework.data.jpa.domain.Specification;

public class BranchSpecs {
    private BranchSpecs() {
    }

    public static Specification<Branch> branchOfProjectNamed(Project project, String branchName) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("project"), project),
                        criteriaBuilder.equal(root.get("name"), branchName)
                )
        );
    }
}
