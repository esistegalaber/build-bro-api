package com.egalaber.buildbro.core.service;

import com.egalaber.buildbro.api.model.IProject;
import com.egalaber.buildbro.core.mapping.ProjectMapper;
import com.egalaber.buildbro.core.repository.ProjectRepository;
import com.egalaber.buildbro.core.repository.ProjectSpecs;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<IProject> projectData(boolean includeInactive) {
        return projectRepository.findAll(ProjectSpecs.allProjects(includeInactive), Sort.by("id"))
                .stream()
                .map(ProjectMapper::toApi)
                .toList();
    }
}
