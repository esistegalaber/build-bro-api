package com.egalaber.buildbro.api.v1;

import com.egalaber.buildbro.api.fault.DataNotFoundException;
import com.egalaber.buildbro.api.model.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public interface DeploymentEndpoint {

    @PostMapping("/api/v1/deployments/search")
    IDeploymentSearchResult search(@RequestBody IDeploymentSearch search);

    @PostMapping("/api/v1/deployments")
    IDeployment create(@RequestBody IDeployment deployment) throws DataNotFoundException;

    @GetMapping("/api/v1/deployments/{serverName}")
    IDeployment current(@PathVariable(name = "serverName") String serverName) throws DataNotFoundException;

    @PostMapping("/api/v1/deployments/add-labels-to-builds/{deploymentId}")
    IDeployment addLabelsToBuilds(@PathVariable("deploymentId") Long deploymentId, @RequestBody List<IBuildLabel> labels) throws DataNotFoundException;

}
