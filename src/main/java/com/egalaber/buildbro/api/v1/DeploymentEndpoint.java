package com.egalaber.buildbro.api.v1;

import com.egalaber.buildbro.api.fault.DataNotFoundException;
import com.egalaber.buildbro.api.model.IDeployment;
import com.egalaber.buildbro.api.model.IDeploymentSearch;
import com.egalaber.buildbro.api.model.IDeploymentSearchResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface DeploymentEndpoint {

    @PostMapping("/api/v1/deployments/search")
    IDeploymentSearchResult search(IDeploymentSearch search);

    @PostMapping("/api/v1/deployments/{server}")
    IDeployment add(@PathVariable(name = "server") String server, @RequestBody List<Long> buildIds) throws DataNotFoundException;

}
