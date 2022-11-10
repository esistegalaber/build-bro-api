package com.egalaber.buildbro.core.repository;

import com.egalaber.buildbro.core.domain.Branch;
import com.egalaber.buildbro.core.domain.Project;
import org.springframework.data.jpa.domain.Specification;

public class BranchSpecs {
    private static final String PROJECT = "project";
    private static final String NAME = "name";
    private static final String ACTIVE = "active";

    private BranchSpecs() {
    }

    public static Specification<Branch> branchOfProjectNamed(Project project, String branchName) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get(BranchSpecs.PROJECT), project),
                        criteriaBuilder.equal(root.get(NAME), branchName)
                )
        );
    }

    public static Specification<Branch> allBranchesOf(Project project, boolean includingInactive) {
        return ((root, query, criteriaBuilder) ->
                includingInactive ? criteriaBuilder.equal(root.get(BranchSpecs.PROJECT), project) :
                        criteriaBuilder.and(
                                criteriaBuilder.equal(root.get(BranchSpecs.PROJECT), project),
                                criteriaBuilder.equal(root.get(ACTIVE), true)
                        )
        );
    }
}
