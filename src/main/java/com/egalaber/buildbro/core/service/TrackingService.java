package com.egalaber.buildbro.core.service;

import com.egalaber.buildbro.api.model.IBuild;
import com.egalaber.buildbro.core.domain.Branch;
import com.egalaber.buildbro.core.domain.Project;
import com.egalaber.buildbro.core.repository.BranchRepository;
import com.egalaber.buildbro.core.repository.BranchSpecs;
import com.egalaber.buildbro.core.repository.ProjectRepository;
import com.egalaber.buildbro.core.repository.ProjectSpecs;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Slf4j
public class TrackingService {
    private final ProjectRepository projectRepository;
    private final BranchRepository branchRepository;

    public TrackingService(ProjectRepository projectRepository, BranchRepository branchRepository) {
        this.projectRepository = projectRepository;
        this.branchRepository = branchRepository;
    }

    public void handleBuildCreated(IBuild build) {
        Project theProject = projectRepository
                .findOne(ProjectSpecs.projectNamed(build.getProject()))
                .orElse(createProject(build.getProject()));
        Branch theBranch = branchRepository
                .findOne(BranchSpecs.branchOfProjectNamed(theProject, build.getBranch()))
                .orElse(createBranch(theProject, build.getBranch()));
        log.info("handleBuildCreated(build={}) - theProject.id={}, theBranch.id={}", build, theProject.getId(), theBranch.getId());
    }

    private Project createProject(String projectName) {
        Project project = new Project();
        project.setName(projectName);
        project.setActive(true);
        return projectRepository.save(project);
    }

    private Branch createBranch(Project project, String branchName) {
        Branch branch = new Branch();
        branch.setProject(project);
        branch.setName(branchName);
        branch.setActive(true);
        branch = branchRepository.save(branch);
        project.getBranches().add(branch);
        return branch;
    }
}
