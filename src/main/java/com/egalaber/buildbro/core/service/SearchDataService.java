package com.egalaber.buildbro.core.service;

import com.egalaber.buildbro.api.model.ISearchData;
import com.egalaber.buildbro.core.domain.Branch;
import com.egalaber.buildbro.core.domain.Project;
import com.egalaber.buildbro.core.repository.BuildLabelRepository;
import com.egalaber.buildbro.core.repository.ProjectRepository;
import com.egalaber.buildbro.core.repository.ProjectSpecs;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
        List<Project> allProjects = projectRepository.findAll(ProjectSpecs.allProjects(false));
        Map<String, List<String>> projectBranches = new LinkedHashMap<>();
        allProjects.forEach(project -> projectBranches.put(
                project.getName(),
                project.getBranches().stream().map(Branch::getName).toList()
        ));
        return new ISearchData(
                allProjects.stream().map(Project::getName).toList(),
                projectBranches,
                List.copyOf(buildLabelRepository.distinctLabelKeys())
        );
    }
}
