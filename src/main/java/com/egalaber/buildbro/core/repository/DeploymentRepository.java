package com.egalaber.buildbro.core.repository;

import com.egalaber.buildbro.core.domain.Deployment;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DeploymentRepository extends PagingAndSortingRepository<Deployment, Long>, JpaSpecificationExecutor<Deployment> {
}
