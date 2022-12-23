package com.egalaber.buildbro.core.repository;

import com.egalaber.buildbro.core.domain.BuildTemplate;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BuildTemplateRepository extends CrudRepository<BuildTemplate, Long>, PagingAndSortingRepository<BuildTemplate, Long>, JpaSpecificationExecutor<BuildTemplate> {
}
