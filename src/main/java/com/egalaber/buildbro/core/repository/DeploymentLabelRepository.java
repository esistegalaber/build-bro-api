package com.egalaber.buildbro.core.repository;

import com.egalaber.buildbro.core.domain.DeploymentLabel;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface DeploymentLabelRepository extends PagingAndSortingRepository<DeploymentLabel, Long>, JpaSpecificationExecutor<DeploymentLabel> {
    @Query("SELECT DISTINCT l.key FROM DeploymentLabel l ORDER BY l.key")
    Set<String> distinctLabelKeys();

    /** Usage of every key/value pair whose key is one of {@code lowerCaseKeys}. */
    @Query("SELECT new com.egalaber.buildbro.core.repository.LabelUsage(l.key, l.value, COUNT(DISTINCT l.deployment.id)) "
            + "FROM DeploymentLabel l WHERE LOWER(l.key) IN :lowerCaseKeys GROUP BY l.key, l.value")
    List<LabelUsage> usagesOf(@Param("lowerCaseKeys") Collection<String> lowerCaseKeys);

    /** Usage of every key that is one of {@code lowerCaseKeys}, whatever the value. */
    @Query("SELECT new com.egalaber.buildbro.core.repository.LabelKeyUsage(l.key, COUNT(DISTINCT l.deployment.id)) "
            + "FROM DeploymentLabel l WHERE LOWER(l.key) IN :lowerCaseKeys GROUP BY l.key")
    List<LabelKeyUsage> keyUsagesOf(@Param("lowerCaseKeys") Collection<String> lowerCaseKeys);
}
