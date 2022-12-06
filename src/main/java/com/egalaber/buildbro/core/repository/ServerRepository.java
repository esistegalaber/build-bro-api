package com.egalaber.buildbro.core.repository;

import com.egalaber.buildbro.core.domain.Server;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ServerRepository extends PagingAndSortingRepository<Server, Long>, JpaSpecificationExecutor<Server> {
}
