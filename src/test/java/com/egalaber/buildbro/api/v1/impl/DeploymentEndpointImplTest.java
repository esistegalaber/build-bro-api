package com.egalaber.buildbro.api.v1.impl;

import com.egalaber.buildbro.api.model.*;
import com.egalaber.buildbro.api.v1.BaseRestTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class DeploymentEndpointImplTest extends BaseRestTest {

    @Test
    public void search() {
        //given
        IDeploymentSearch search = new IDeploymentSearch();
        String SEARCH_URL = baseUrl() + "/deployments/search";
        //when
        ResponseEntity<IDeploymentSearchResult> responseEntity = restTemplate.postForEntity(SEARCH_URL, search, IDeploymentSearchResult.class);
        //then
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertFalse(responseEntity.getBody().getData().isEmpty());
    }

    @Test
    public void addDeployment() {
        //given
        String serverName = "prod-1";
        IDeployment newDeployment = new IDeployment();
        newDeployment.setServerName(serverName);
        newDeployment.getBuilds().add(build(1L));
        newDeployment.getBuilds().add(build(2L));
        newDeployment.getBuilds().add(build(3L));
        newDeployment.getLabels().add(deploymentLabel("additional", "info"));
        String ADD_URL = baseUrl() + "/deployments/" + serverName;
        //when
        ResponseEntity<IDeployment> responseEntity = restTemplate.postForEntity(ADD_URL, newDeployment, IDeployment.class);
        //then
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertFalse(responseEntity.getBody().getBuilds().isEmpty());
        Assertions.assertFalse(responseEntity.getBody().getLabels().isEmpty());
    }

    private IBuild build(Long buildId) {
        IBuild iBuild = new IBuild();
        iBuild.setId(buildId);
        return iBuild;
    }

    private IDeploymentLabel deploymentLabel(String key, String value) {
        IDeploymentLabel iDeploymentLabel = new IDeploymentLabel();
        iDeploymentLabel.setKey(key);
        iDeploymentLabel.setValue(value);
        return iDeploymentLabel;
    }
}
