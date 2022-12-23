package com.egalaber.buildbro.core.service;

import com.egalaber.buildbro.api.fault.DataNotFoundException;
import com.egalaber.buildbro.api.model.IBranch;
import com.egalaber.buildbro.api.model.IProject;
import com.egalaber.buildbro.core.domain.Branch;
import com.egalaber.buildbro.core.domain.Project;
import com.egalaber.buildbro.core.mapping.BranchMapper;
import com.egalaber.buildbro.core.mapping.ProjectMapper;
import com.egalaber.buildbro.core.repository.BranchRepository;
import com.egalaber.buildbro.core.repository.ProjectRepository;
import com.egalaber.buildbro.core.repository.ProjectSpecs;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final BranchRepository branchRepository;

    public ProjectService(ProjectRepository projectRepository, BranchRepository branchRepository) {
        this.projectRepository = projectRepository;
        this.branchRepository = branchRepository;
    }

    public List<IProject> projectData(boolean includeInactive) {
        return projectRepository.findAll(ProjectSpecs.allProjects(includeInactive), Sort.by("id"))
                .stream()
                .map(ProjectMapper::toApi)
                .toList();
    }

    public IProject setProjectActive(Long projectId, boolean active) throws DataNotFoundException {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new DataNotFoundException("No Project found with id='" + projectId + "'"));
        project.setActive(active);
        return ProjectMapper.toApi(projectRepository.save(project));
    }

    public IBranch setBranchActive(Long branchId, boolean active) throws DataNotFoundException {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new DataNotFoundException("No Branch found with id='" + branchId + "'"));
        branch.setActive(active);
        return BranchMapper.toApi(branchRepository.save(branch));
    }
}
