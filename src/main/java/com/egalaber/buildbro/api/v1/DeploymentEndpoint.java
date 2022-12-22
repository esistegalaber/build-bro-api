package com.egalaber.buildbro.api.v1;

import com.egalaber.buildbro.api.fault.DataNotFoundException;
import com.egalaber.buildbro.api.model.IDeployment;
import com.egalaber.buildbro.api.model.IDeploymentSearch;
import com.egalaber.buildbro.api.model.IDeploymentSearchResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface DeploymentEndpoint {

    @PostMapping("/api/v1/deployments/search")
    IDeploymentSearchResult search(@RequestBody IDeploymentSearch search);

    @PostMapping("/api/v1/deployments/{server}")
    IDeployment add(@RequestBody IDeployment deployment) throws DataNotFoundException;

}
