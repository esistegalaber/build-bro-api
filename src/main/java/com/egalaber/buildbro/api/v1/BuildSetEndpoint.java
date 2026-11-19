package com.egalaber.buildbro.api.v1;

import com.egalaber.buildbro.api.fault.DataNotFoundException;
import com.egalaber.buildbro.api.fault.InvalidRequestException;
import com.egalaber.buildbro.api.model.IBuildSet;
import com.egalaber.buildbro.api.model.IBuildSetTemplate;
import com.egalaber.buildbro.api.model.IBuildTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

@Tag(name = "Build sets", description = """
        Build-set templates describe a set of builds by rules, e.g. an environment: "backend from main labelled \
        qa=passed, frontend from main". Resolving a template returns the concrete builds that currently match.""")
public interface BuildSetEndpoint {

    @Operation(
            operationId = "listBuildSetNames",
            summary = "List build-set names")
    @GetMapping("/api/v1/build-sets/names")
    List<String> names();

    @Operation(
            operationId = "listBuildSets",
            summary = "List build-set templates")
    @GetMapping("/api/v1/build-sets")
    List<IBuildSetTemplate> all();

    @Operation(
            operationId = "saveBuildSet",
            summary = "Create or update a build-set template",
            description = """
                    Without an `id` a new template is created; its name must be unique. With an `id` the existing \
                    template is updated and its build templates are replaced by the ones sent.""")
    @PostMapping(value = "/api/v1/build-sets")
    IBuildSetTemplate save(@RequestBody IBuildSetTemplate environment) throws InvalidRequestException;

    @Operation(
            operationId = "getBuildSet",
            summary = "Get a build-set template")
    @GetMapping("/api/v1/build-sets/{name}")
    IBuildSetTemplate get(@Parameter(description = "Build-set name", example = "staging") @PathVariable("name") String name)
            throws DataNotFoundException;

    @Operation(
            operationId = "resolveBuildTemplates",
            summary = "Resolve ad-hoc build templates",
            description = """
                    Resolves the given build templates without saving them, e.g. to check what a template would \
                    select. Templates that match no build are left out of the result.""")
    @PostMapping(value = "/api/v1/build-sets/verify")
    IBuildSet verify(@RequestBody Set<IBuildTemplate> artifacts);

    @Operation(
            operationId = "resolveBuildSet",
            summary = "Resolve a build-set template",
            description = """
                    Returns, per project, the build each template of the build set selects: the pinned build \
                    number if set, else the latest build matching branch and labels. Templates that match no \
                    build are left out.""")
    @GetMapping("/api/v1/build-sets/of/{name}")
    IBuildSet buildsOf(@Parameter(description = "Build-set name", example = "staging") @PathVariable(name = "name") String name)
            throws DataNotFoundException;

    @Operation(
            operationId = "deleteBuildSet",
            summary = "Delete a build-set template", description = "Does nothing if no build set has that name.")
    @DeleteMapping("/api/v1/build-sets/{name}")
    void delete(@Parameter(description = "Build-set name", example = "staging") @PathVariable("name") String name);
}
