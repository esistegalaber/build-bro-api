package com.egalaber.buildbro.core.repository;

import com.egalaber.buildbro.core.domain.LabelDefinition;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LabelDefinitionRepository extends CrudRepository<LabelDefinition, Long>, PagingAndSortingRepository<LabelDefinition, Long>, JpaSpecificationExecutor<LabelDefinition> {
}
