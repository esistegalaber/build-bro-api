package com.egalaber.buildbro.api.v1.impl;

import com.egalaber.buildbro.api.model.*;
import com.egalaber.buildbro.api.v1.BaseRestTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

class DeploymentEndpointImplTest extends BaseRestTest {

    @Test
    void search() {
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
    void createDeployment() {
        //given
        String serverName = "prod-1";
        IDeployment newDeployment = new IDeployment();
        newDeployment.setServerName(serverName);
        newDeployment.getBuilds().add(build(1L));
        newDeployment.getBuilds().add(build(2L));
        newDeployment.getBuilds().add(build(3L));
        newDeployment.getLabels().add(deploymentLabel("additional", "info"));
        String ADD_URL = baseUrl() + "/deployments";
        //when
        ResponseEntity<IDeployment> responseEntity = restTemplate.postForEntity(ADD_URL, newDeployment, IDeployment.class);
        //then
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertFalse(responseEntity.getBody().getBuilds().isEmpty());
        Assertions.assertFalse(responseEntity.getBody().getLabels().isEmpty());
    }

    @Test
    void currentDeployment() {
        String CURRENT_URL = baseUrl() + "/deployments/testserver";
        ResponseEntity<IDeployment> current = restTemplate.getForEntity(CURRENT_URL, IDeployment.class);
        Assertions.assertEquals(HttpStatus.OK, current.getStatusCode());
        Assertions.assertFalse(current.getBody().getBuilds().isEmpty());
    }

    @Test
    void addLabelsToBuilds() {
        String ADD_BUILD_LABELS_URL = baseUrl() + "/deployments/add-labels-to-builds/1";
        List<IBuildLabel> labelList = new ArrayList<>();
        labelList.add(buildLabel("test.42.status", "broken"));
        labelList.add(buildLabel("test.42.triggered", "josip"));
        ResponseEntity<IDeployment> current = restTemplate.postForEntity(ADD_BUILD_LABELS_URL, labelList, IDeployment.class);
        Assertions.assertEquals(HttpStatus.OK, current.getStatusCode());
        Assertions.assertFalse(current.getBody().getBuilds().isEmpty());
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

    private IBuildLabel buildLabel(String key, String value) {
        IBuildLabel label = new IBuildLabel();
        label.setKey(key);
        label.setValue(value);
        return label;
    }
}
