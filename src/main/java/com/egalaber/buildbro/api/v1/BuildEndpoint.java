package com.egalaber.buildbro.api.v1;

import com.egalaber.buildbro.api.fault.DataNotFoundException;
import com.egalaber.buildbro.api.fault.InvalidRequestException;
import com.egalaber.buildbro.api.model.IBuild;
import com.egalaber.buildbro.api.model.IBuildSearch;
import com.egalaber.buildbro.api.model.IBuildSearchResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Tag(name = "Builds", description = "Register builds produced by CI pipelines, label them and search for them.")
public interface BuildEndpoint {

    @Operation(
            operationId = "searchBuilds",
            summary = "Search builds",
            description = """
                    Pages through builds matching all given criteria; every criterion is optional, so `{}` returns \
                    the latest builds. `minBuildNumber`/`maxBuildNumber` are exclusive bounds. When `labels` are \
                    given, a build matches if it carries any of them (key and value must both match). \
                    Defaults: page 0, 10 per page, sorted by `buildNumber` descending.""")
    @PostMapping("/api/v1/builds/search")
    IBuildSearchResult search(@RequestBody IBuildSearch search);

    @Operation(
            operationId = "getBuild",
            summary = "Get a build", description = "Returns a single build, including its labels.")
    @GetMapping("/api/v1/builds/{buildId}")
    IBuild get(@Parameter(description = "Build id", example = "1") @PathVariable(name = "buildId") Long buildId)
            throws DataNotFoundException;

    @Operation(
            operationId = "createBuild",
            summary = "Register a build",
            description = """
                    Records a finished build. Project and branch are created on the fly the first time a build \
                    for them arrives. The id in the request is ignored. Fails if a build with the same project, \
                    branch and build number already exists.""")
    @PostMapping("/api/v1/builds/create")
    IBuild create(@RequestBody IBuild build) throws InvalidRequestException;

    @Operation(
            operationId = "addBuildLabels",
            summary = "Add labels to a build",
            description = "Attaches additional key/value labels to an existing build, e.g. once it has passed further testing.")
    @PostMapping("/api/v1/builds/add-labels/{buildId}")
    IBuild addLabels(
            @Parameter(description = "Build id", example = "1") @PathVariable(name = "buildId") Long buildId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Labels to add, as key/value pairs",
                    content = @Content(examples = @ExampleObject(value = "{\"qa\": \"passed\", \"docker-image\": \"backend:42\"}")))
            @RequestBody Map<String, String> buildLabels) throws DataNotFoundException;
}
