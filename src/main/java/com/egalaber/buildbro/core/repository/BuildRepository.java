package com.egalaber.buildbro.core.repository;

import com.egalaber.buildbro.core.domain.Build;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface BuildRepository extends PagingAndSortingRepository<Build, Long>, JpaSpecificationExecutor<Build> {
    @Query("SELECT DISTINCT b.project FROM Build b ORDER BY b.project ASC")
    List<String> distinctProjects();

    @Query("SELECT DISTINCT b.branch FROM Build b ORDER BY b.branch ASC")
    Set<String> distinctBranches();

    @Query("SELECT DISTINCT b.branch FROM Build b WHERE b.project=:project ORDER BY b.branch ASC")
    List<String> distinctBranchesOf(@Param("project") String project);
}
