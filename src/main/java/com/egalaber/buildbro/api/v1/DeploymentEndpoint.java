package com.egalaber.buildbro.api.v1;

import com.egalaber.buildbro.api.fault.DataNotFoundException;
import com.egalaber.buildbro.api.model.IBuildLabel;
import com.egalaber.buildbro.api.model.IDeployment;
import com.egalaber.buildbro.api.model.IDeploymentSearch;
import com.egalaber.buildbro.api.model.IDeploymentSearchResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Deployments", description = "Record which builds were deployed to which server, and when.")
public interface DeploymentEndpoint {

    @Operation(
            operationId = "searchDeployments",
            summary = "Search deployments",
            description = "Pages through deployments, optionally of one server. Defaults: page 0, 10 per page, newest first.")
    @PostMapping("/api/v1/deployments/search")
    IDeploymentSearchResult search(@RequestBody IDeploymentSearch search);

    @Operation(
            operationId = "createDeployment",
            summary = "Record a deployment",
            description = """
                    Records that the given builds (referenced by `id`; other build fields are ignored) were \
                    deployed to `serverName` now. The server is created if it is not known yet. Build ids that \
                    do not exist are skipped.""")
    @PostMapping("/api/v1/deployments")
    IDeployment create(@RequestBody IDeployment deployment) throws DataNotFoundException;

    @Operation(
            operationId = "currentDeployment",
            summary = "Current deployment of a server", description = "Returns the latest deployment to the server.")
    @GetMapping("/api/v1/deployments/{serverName}")
    IDeployment current(@Parameter(description = "Server name", example = "staging-01") @PathVariable(name = "serverName") String serverName)
            throws DataNotFoundException;

    @Operation(
            operationId = "addLabelsToDeployedBuilds",
            summary = "Label all builds of a deployment",
            description = "Adds the labels to every build of the deployment, e.g. `qa=passed` after tests ran against it.")
    @PostMapping("/api/v1/deployments/add-labels-to-builds/{deploymentId}")
    IDeployment addLabelsToBuilds(
            @Parameter(description = "Deployment id", example = "1") @PathVariable("deploymentId") Long deploymentId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Labels to add; ids are ignored",
                    content = @Content(examples = @ExampleObject(value = "[{\"key\": \"qa\", \"value\": \"passed\"}]")))
            @RequestBody List<IBuildLabel> labels) throws DataNotFoundException;
}
