package com.egalaber.buildbro.api.v1.impl;

import com.egalaber.buildbro.api.model.IBranch;
import com.egalaber.buildbro.api.model.IProject;
import com.egalaber.buildbro.api.v1.BaseRestTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import spock.lang.Ignore;

import java.util.List;

@Ignore
class ProjectEndpointImplTest extends BaseRestTest {
    @Test
    void getProjectData() {
        //given
        String GET_URL = baseUrl() + "/projects";
        //when
        ResponseEntity<List> responseEntity = restTemplate.getForEntity(GET_URL, List.class);
        //then
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertFalse(responseEntity.getBody().isEmpty());
    }

    @Test
    void toggleProjectActive() {
        //given
        Long projectId = 1L;
        String ACTIVATE_URL = baseUrl() + "/projects/active/" + projectId;
        String DE_ACTIVATE_URL = baseUrl() + "/projects/inactive/" + projectId;
        String WRONG_ID_URL = baseUrl() + "/projects/inactive/-1";
        //when
        ResponseEntity<IProject> deActivation = restTemplate.postForEntity(DE_ACTIVATE_URL, null, IProject.class);
        ResponseEntity<IProject> activation = restTemplate.postForEntity(ACTIVATE_URL, null, IProject.class);
        ResponseEntity<IProject> notFoundCall = restTemplate.postForEntity(WRONG_ID_URL, null, IProject.class);
        //then
        Assertions.assertEquals(HttpStatus.OK, deActivation.getStatusCode());
        Assertions.assertEquals(HttpStatus.OK, activation.getStatusCode());
        //and
        Assertions.assertTrue(activation.getBody().getActive());
        Assertions.assertFalse(deActivation.getBody().getActive());
        //and
        Assertions.assertEquals(HttpStatus.NOT_FOUND, notFoundCall.getStatusCode());
    }

    @Test
    void toggleBranchActive() {
        //given
        Long branchId = 1L;
        String ACTIVATE_URL = baseUrl() + "/projects/branch-active/" + branchId;
        String DE_ACTIVATE_URL = baseUrl() + "/projects/branch-inactive/" + branchId;
        String WRONG_ID_URL = baseUrl() + "/projects/branch-inactive/-1";
        //when
        ResponseEntity<IBranch> deActivation = restTemplate.postForEntity(DE_ACTIVATE_URL, null, IBranch.class);
        ResponseEntity<IBranch> activation = restTemplate.postForEntity(ACTIVATE_URL, null, IBranch.class);
        ResponseEntity<IBranch> notFoundCall = restTemplate.postForEntity(WRONG_ID_URL, null, IBranch.class);
        //then
        Assertions.assertEquals(HttpStatus.OK, deActivation.getStatusCode());
        Assertions.assertEquals(HttpStatus.OK, activation.getStatusCode());
        //and
        Assertions.assertTrue(activation.getBody().getActive());
        Assertions.assertFalse(deActivation.getBody().getActive());
        //and
        Assertions.assertEquals(HttpStatus.NOT_FOUND, notFoundCall.getStatusCode());
    }
}
