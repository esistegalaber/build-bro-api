package com.egalaber.buildbro.core.repository;

import com.egalaber.buildbro.core.domain.Build;
import com.egalaber.buildbro.core.domain.BuildLabel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

public class BuildSpecs {
    private static final String PROJECT = "project";
    private static final String BRANCH = "branch";
    private static final String BUILD_NUMBER = "buildNumber";
    private static final String LABELS = "labels";

    private BuildSpecs() {
    }


    public static Specification<Build> allBuilds() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.conjunction()
        );
    }

    public static Specification<Build> noBuilds() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.disjunction()
        );
    }

    public static Specification<Build> findSpecificBuild(@NonNull String project, @NonNull String branch, Long buildNumber) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get(PROJECT), project),
                criteriaBuilder.equal(root.get(BRANCH), branch),
                criteriaBuilder.equal(root.get(BUILD_NUMBER), buildNumber)
        );
    }

    public static Specification<Build> hasLabel(BuildLabel buildLabel) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.isMember(buildLabel, root.get(LABELS))
        );
    }

    public static Specification<Build> ofProject(String project) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get(PROJECT), project)
        );
    }

    public static Specification<Build> onBranch(String branch) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get(BRANCH), branch)
        );
    }

    public static Specification<Build> minBuildNumber(Long minBuildNumber) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.gt(root.get(BUILD_NUMBER), minBuildNumber)
        );
    }

    public static Specification<Build> maxBuildNumber(Long maxBuildNumber) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.lt(root.get(BUILD_NUMBER), maxBuildNumber)
        );
    }
}
