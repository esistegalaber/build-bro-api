package com.egalaber.buildbro.core.repository;

import com.egalaber.buildbro.core.domain.Branch;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BranchRepository extends PagingAndSortingRepository<Branch, Long>, JpaSpecificationExecutor<Branch> {
}
