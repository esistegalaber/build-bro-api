package com.egalaber.buildbro.core.repository;

import com.egalaber.buildbro.core.domain.BuildNumber;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BuildNumberRepository extends CrudRepository<BuildNumber, Long>, PagingAndSortingRepository<BuildNumber, Long>, JpaSpecificationExecutor<BuildNumber> {
}
