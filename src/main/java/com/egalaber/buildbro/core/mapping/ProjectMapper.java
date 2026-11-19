package com.egalaber.buildbro.core.mapping;

import com.egalaber.buildbro.api.model.IProject;
import com.egalaber.buildbro.core.domain.Project;

public class ProjectMapper {
    private ProjectMapper() {
    }

    public static IProject toApi(Project project) {
        return new IProject(
                project.getId(),
                project.getName(),
                project.getActive(),
                project.getBranches().stream().map(BranchMapper::toApi).toList()
        );
    }
}
