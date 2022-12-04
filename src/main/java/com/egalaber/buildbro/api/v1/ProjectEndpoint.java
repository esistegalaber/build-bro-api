package com.egalaber.buildbro.api.v1;

import com.egalaber.buildbro.api.fault.DataNotFoundException;
import com.egalaber.buildbro.api.model.IBranch;
import com.egalaber.buildbro.api.model.IProject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

public interface ProjectEndpoint {
    @GetMapping("/api/v1/projects")
    List<IProject> getProjectData();

    @PostMapping("/api/v1/projects/active/{projectId}")
    IProject projectActive(@PathVariable("projectId") Long projectId) throws DataNotFoundException;

    @PostMapping("/api/v1/projects/inactive/{projectId}")
    IProject projectInactive(@PathVariable("projectId") Long projectId) throws DataNotFoundException;

    @PostMapping("/api/v1/projects/branch-active/{branchId}")
    IBranch branchActive(@PathVariable("branchId") Long branchId) throws DataNotFoundException;

    @PostMapping("/api/v1/projects/branch-inactive/{branchId}")
    IBranch branchInactive(@PathVariable("branchId") Long branchId) throws DataNotFoundException;
}
