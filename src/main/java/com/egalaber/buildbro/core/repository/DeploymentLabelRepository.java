package com.egalaber.buildbro.core.repository;

import com.egalaber.buildbro.core.domain.DeploymentLabel;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Set;

public interface DeploymentLabelRepository extends PagingAndSortingRepository<DeploymentLabel, Long>, JpaSpecificationExecutor<DeploymentLabel> {
    @Query("SELECT DISTINCT l.key FROM DeploymentLabel l ORDER BY l.key")
    Set<String> distinctLabelKeys();
}
