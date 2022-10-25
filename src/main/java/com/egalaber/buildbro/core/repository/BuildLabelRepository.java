package com.egalaber.buildbro.core.repository;

import com.egalaber.buildbro.core.domain.BuildLabel;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BuildLabelRepository extends PagingAndSortingRepository<BuildLabel, Long>, JpaSpecificationExecutor<BuildLabel> {
}
