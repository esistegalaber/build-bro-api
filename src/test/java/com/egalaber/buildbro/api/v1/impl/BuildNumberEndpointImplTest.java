package com.egalaber.buildbro.api.v1.impl;

import com.egalaber.buildbro.api.model.IBuildNumber;
import com.egalaber.buildbro.api.v1.BaseRestTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class BuildNumberEndpointImplTest extends BaseRestTest {
    @Test
    void currentBuildNumberOfExistingProject() {
        //given
        String GET_CURRENT_URL = "http://localhost:" + port + "/api/v1/build-numbers/current/backend/main";

        //when
        ResponseEntity<IBuildNumber> current = restTemplate.getForEntity(GET_CURRENT_URL, IBuildNumber.class);
        IBuildNumber buildNumber = current.getBody();

        //then
        Assertions.assertEquals(HttpStatus.OK, current.getStatusCode());
        Assertions.assertEquals(10, buildNumber.getNumber());
    }

    //
    @Test
    void CurrentBuildNumberOfNewProject() {
        //given
        String GET_CURRENT_URL = "http://localhost:" + port + "/api/v1/build-numbers/current/my-new-project/master";

        //when
        ResponseEntity<IBuildNumber> first = restTemplate.getForEntity(GET_CURRENT_URL, IBuildNumber.class);
        ResponseEntity<IBuildNumber> second = restTemplate.getForEntity(GET_CURRENT_URL, IBuildNumber.class);

        //then
        Assertions.assertEquals(HttpStatus.OK, first.getStatusCode());
        Assertions.assertEquals(HttpStatus.OK, second.getStatusCode());
        Assertions.assertEquals(first.getBody().getNumber(), second.getBody().getNumber());
    }

    @Test
    void testNextAndSetNew() {
        //given
        String project = "backend";
        String branch = "feature/feature-backend";
        IBuildNumber nextBuildCount = new IBuildNumber();
        nextBuildCount.setProject(project);
        nextBuildCount.setBranch(branch);

        IBuildNumber setBuildCount = new IBuildNumber();
        setBuildCount.setProject(project);
        setBuildCount.setBranch(branch);
        setBuildCount.setNumber(20L);

        String NEXT_URL = "http://localhost:" + port + "/api/v1/build-numbers/next";
        String SET_URL = "http://localhost:" + port + "/api/v1/build-numbers/set";

        //when
        //Call next
        ResponseEntity<IBuildNumber> nextResponse = restTemplate.postForEntity(NEXT_URL, nextBuildCount, IBuildNumber.class);
        ResponseEntity<IBuildNumber> setResponse = restTemplate.postForEntity(SET_URL, setBuildCount, IBuildNumber.class);

        //then
        Assertions.assertEquals(HttpStatus.OK, nextResponse.getStatusCode());
        Assertions.assertEquals(HttpStatus.OK, setResponse.getStatusCode());
        Assertions.assertEquals(11, nextResponse.getBody().getNumber());
        Assertions.assertEquals(20, setResponse.getBody().getNumber());
    }
}
