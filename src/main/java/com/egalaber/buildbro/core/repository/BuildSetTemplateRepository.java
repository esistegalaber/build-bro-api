package com.egalaber.buildbro.core.repository;

import com.egalaber.buildbro.core.domain.BuildSetTemplate;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BuildSetTemplateRepository extends PagingAndSortingRepository<BuildSetTemplate, Long>, JpaSpecificationExecutor<BuildSetTemplate> {
    @Query("SELECT DISTINCT bs.name FROM BuildSetTemplate bs ORDER BY bs.id")
    List<String> allNames();
}
