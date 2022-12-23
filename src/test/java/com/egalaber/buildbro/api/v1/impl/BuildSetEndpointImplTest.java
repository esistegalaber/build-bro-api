package com.egalaber.buildbro.api.v1.impl;

import com.egalaber.buildbro.api.model.IBuildSet;
import com.egalaber.buildbro.api.model.IBuildSetTemplate;
import com.egalaber.buildbro.api.model.IBuildTemplate;
import com.egalaber.buildbro.api.model.IExceptionInfo;
import com.egalaber.buildbro.api.v1.BaseRestTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import spock.lang.Ignore;

import java.util.Arrays;
import java.util.List;

@Ignore
class BuildSetEndpointImplTest extends BaseRestTest {
    @Test
    void CreateNewUpdateThenDeleteIt() {
        //given
        String newEnvName = "new-crazy-env";
        String SAVE_URL = baseUrl() + "/build-sets";
        String DELETE_URL = baseUrl() + "/build-sets/" + newEnvName;
        IBuildSetTemplate newEnvironment = new IBuildSetTemplate();
        newEnvironment.setName(newEnvName);
        IBuildTemplate buildTemplate = new IBuildTemplate();
        buildTemplate.setProject("backend");
        buildTemplate.setBranch("main");
        newEnvironment.getBuildTemplates().add(buildTemplate);

        //when
        ResponseEntity<IBuildSetTemplate> created = restTemplate.postForEntity(SAVE_URL, newEnvironment, IBuildSetTemplate.class);
        IBuildSetTemplate toUpdate = created.getBody();

        IBuildTemplate anotherOne = new IBuildTemplate();
        anotherOne.setProject("'frontend'");
        anotherOne.setBranch("main");
        anotherOne.getLabels().put("integration-test", "ok");
        toUpdate.getBuildTemplates().add(buildTemplate);
        toUpdate.getBuildTemplates().add(anotherOne);
        ResponseEntity<IBuildSetTemplate> updated = restTemplate.postForEntity(SAVE_URL, toUpdate, IBuildSetTemplate.class);
        restTemplate.delete(DELETE_URL);

        //then
        Assertions.assertEquals(HttpStatus.OK, created.getStatusCode());
        Assertions.assertEquals(HttpStatus.OK, updated.getStatusCode());
        Assertions.assertEquals(created.getBody().getId(), updated.getBody().getId());
    }

    @Test
    void testGetSingle() {
        //given
        String GET_URL = baseUrl() + "/build-sets/main";

        //when
        ResponseEntity<IBuildSetTemplate> responseEntity = restTemplate.getForEntity(GET_URL, IBuildSetTemplate.class);

        //then
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody().getId());
    }

    @Test
    void getMissingBuildSet() {
        //given
        String envName = "wrongName";
        String GET_URL = baseUrl() + "/build-sets/ " + envName;

        //when
        ResponseEntity<IExceptionInfo> responseEntity = restTemplate.getForEntity(GET_URL, IExceptionInfo.class);

        //then
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertEquals("not-found", responseEntity.getBody().getKey());
    }

    @Test
    void listAllBuildSetNames() {
        //given
        String LIST_URL = baseUrl() + "/build-sets/names";

        //when
        ResponseEntity<List> responseEntity = restTemplate.getForEntity(LIST_URL, List.class);

        //then
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertFalse(responseEntity.getBody().isEmpty());
    }

    @Test
    void verifyBuildSetWithOneBuildTemplate() {
        //given
        String VERIFY_URL = baseUrl() + "/build-sets/verify";
        IBuildTemplate artifact = new IBuildTemplate();
        artifact.setProject("backend");
        artifact.setBranch("main");
        artifact.getLabels().put("integration-test", "OK");
        //when
        ResponseEntity<IBuildSet> responseEntity = restTemplate.postForEntity(VERIFY_URL, Arrays.asList(artifact), IBuildSet.class);

        //then
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(1, responseEntity.getBody().getBuilds().size());
    }

    @Test
    void getBuildSetOfDefinedEnvironment() {
        //given
        String templateName = "main";
        String VERIFY_URL = baseUrl() + "/build-sets/of/" + templateName;
        //when
        ResponseEntity<IBuildSet> responseEntity = restTemplate.getForEntity(VERIFY_URL, IBuildSet.class);

        //then
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(2, responseEntity.getBody().getBuilds().size());
    }

    @Test
    void allBuildSets() {
        //given
        String VERIFY_URL = baseUrl() + "/build-sets";
        //when
        ResponseEntity<List> responseEntity = restTemplate.getForEntity(VERIFY_URL, List.class);
        //then
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(responseEntity.getBody().size() > 1);
    }
}
