package com.egalaber.buildbro.core.service;

import com.egalaber.buildbro.api.model.ISearchData;
import com.egalaber.buildbro.core.domain.Branch;
import com.egalaber.buildbro.core.domain.Project;
import com.egalaber.buildbro.core.repository.BuildLabelRepository;
import com.egalaber.buildbro.core.repository.ProjectRepository;
import com.egalaber.buildbro.core.repository.ProjectSpecs;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class SearchDataService {
    private final ProjectRepository projectRepository;
    private final BuildLabelRepository buildLabelRepository;

    public SearchDataService(ProjectRepository projectRepository, BuildLabelRepository buildLabelRepository) {
        this.projectRepository = projectRepository;
        this.buildLabelRepository = buildLabelRepository;
    }

    public ISearchData searchData() {
        final ISearchData searchData = new ISearchData();
        List<Project> allProjects = projectRepository.findAll(ProjectSpecs.allProjects(false));
        searchData.getProjectNames().addAll(allProjects.stream().map(Project::getName).toList());
        allProjects
                .forEach(project -> searchData.getProjectBranches().put(
                        project.getName(),
                        project.getBranches().stream()
                                .map(Branch::getName)
                                .toList()
                ));
        searchData.getLabelKeys().addAll(buildLabelRepository.distinctLabelKeys());
        return searchData;
    }
}
