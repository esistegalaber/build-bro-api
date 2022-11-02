package com.egalaber.buildbro.core.service;

import com.egalaber.buildbro.api.model.IBuildBroStats;
import com.egalaber.buildbro.core.repository.BranchRepository;
import com.egalaber.buildbro.core.repository.BuildLabelRepository;
import com.egalaber.buildbro.core.repository.BuildRepository;
import com.egalaber.buildbro.core.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class StatsService {
    private final BuildRepository buildRepository;
    private final BuildLabelRepository buildLabelRepository;
    private final ProjectRepository projectRepository;
    private final BranchRepository branchRepository;

    public StatsService(BuildRepository buildRepository, BuildLabelRepository buildLabelRepository, ProjectRepository projectRepository, BranchRepository branchRepository) {
        this.buildRepository = buildRepository;
        this.buildLabelRepository = buildLabelRepository;
        this.projectRepository = projectRepository;
        this.branchRepository = branchRepository;
    }

    public IBuildBroStats stats() {
        IBuildBroStats IBuildBroStats = new IBuildBroStats();
        IBuildBroStats.setNumberOfProjects(projectRepository.count());
        IBuildBroStats.setNumberOfBranches(branchRepository.count());
        IBuildBroStats.setNumberOfBuilds(buildRepository.count());
        IBuildBroStats.setNumberOfLabels(buildLabelRepository.count());
        return IBuildBroStats;
    }
}
