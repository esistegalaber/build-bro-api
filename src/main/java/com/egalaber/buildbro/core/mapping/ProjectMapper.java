package com.egalaber.buildbro.core.mapping;

import com.egalaber.buildbro.api.model.IProject;
import com.egalaber.buildbro.core.domain.Project;

public class ProjectMapper {
    private ProjectMapper() {
    }

    public static IProject toApi(Project project) {
        IProject toReturn = new IProject();
        toReturn.setId(project.getId());
        toReturn.setName(project.getName());
        toReturn.setActive(project.getActive());
        toReturn.getBranches().addAll(
                project.getBranches().stream()
                        .map(BranchMapper::toApi)
                        .toList()
        );
        return toReturn;
    }
}
