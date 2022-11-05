package com.egalaber.buildbro.core.repository;

import com.egalaber.buildbro.core.domain.BuildLabel;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Set;

public interface BuildLabelRepository extends PagingAndSortingRepository<BuildLabel, Long>, JpaSpecificationExecutor<BuildLabel> {
    @Query("SELECT DISTINCT l.key FROM BuildLabel l ORDER BY l.key")
    Set<String> distinctLabelKeys();
}
