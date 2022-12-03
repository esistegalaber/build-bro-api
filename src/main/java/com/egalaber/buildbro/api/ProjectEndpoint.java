package com.egalaber.buildbro.api;

import com.egalaber.buildbro.api.model.IProject;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface ProjectEndpoint {
    @GetMapping("/api/v1/projects")
    List<IProject> getProjectData();
}
