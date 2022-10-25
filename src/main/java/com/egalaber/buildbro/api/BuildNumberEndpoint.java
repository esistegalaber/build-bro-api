package com.egalaber.buildbro.api;

import com.egalaber.buildbro.api.model.IBuildNumber;
import com.egalaber.buildbro.api.model.ProjectBranch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface BuildNumberEndpoint {

    @PostMapping("/api/v1/build-numbers/next")
    IBuildNumber next(
            @RequestBody ProjectBranch projectBranch
    );

    @GetMapping("/api/v1/build-numbers/current/{project}/{branch}")
    IBuildNumber current(
            @PathVariable(name = "project")
            String project,
            @PathVariable(name = "branch")
            String branch
    );

    @PostMapping("/api/v1/build-numbers/set")
    IBuildNumber set(@RequestBody IBuildNumber buildNumber);
}
