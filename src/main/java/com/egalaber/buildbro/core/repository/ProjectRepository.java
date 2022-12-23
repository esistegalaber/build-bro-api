package com.egalaber.buildbro.core.repository;

import com.egalaber.buildbro.core.domain.Project;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProjectRepository extends CrudRepository<Project, Long>, PagingAndSortingRepository<Project, Long>, JpaSpecificationExecutor<Project> {
}
