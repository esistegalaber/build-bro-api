package com.egalaber.buildbro.api.v1.impl;

import com.egalaber.buildbro.api.model.IBuild;
import com.egalaber.buildbro.api.model.IBuildLabel;
import com.egalaber.buildbro.api.model.IBuildSearch;
import com.egalaber.buildbro.api.model.IBuildSearchResult;
import com.egalaber.buildbro.api.v1.BaseRestTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class BuildEndpointImplTest extends BaseRestTest {
    @Test
    void emtpyDefaultSearch() {
        IBuildSearch search = new IBuildSearch();
        ResponseEntity<IBuildSearchResult> responseEntity = restTemplate.postForEntity(searchUrl(), search, IBuildSearchResult.class);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertFalse(responseEntity.getBody().getData().isEmpty());
    }

    @Test
    void searchUsingProjectAndLabel() {
        //given
        IBuildSearch search = new IBuildSearch();
        search.setProject("backend");
        search.getLabels().put("integration-test", "broken");

        //when
        ResponseEntity<IBuildSearchResult> responseEntity = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/builds/search", search, IBuildSearchResult.class);
        //then
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertFalse(responseEntity.getBody().getData().isEmpty());
    }

    @Test
    void loadSingleProject() {
        //given
        Long buildId = 1L;
        //when
        ResponseEntity<IBuild> responseEntity = restTemplate.getForEntity(
                getSingleBuildUrl(buildId), IBuild.class
        );
        //then
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(buildId, responseEntity.getBody().getId());
    }

    @Test
    void loadMissingProject() {
        //given
        Long buildId = -1L;
        //when
        ResponseEntity<IBuild> responseEntity = restTemplate.getForEntity(
                getSingleBuildUrl(buildId), IBuild.class
        );
        //then
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void createBuild() {
        //given
        IBuild build = new IBuild();
        build.setProject("backend");
        build.setBranch("'hotfix");
        build.setBuildNumber(1L);
        //when
        ResponseEntity<IBuild> responseEntity = restTemplate.postForEntity(createUrl(), build, IBuild.class);
        //then
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody().getId());
    }

    @Test
    void addLabels() {
        //given
        Long buildId = 1L;
        Map<String, String> newLabels = new HashMap<>();
        newLabels.put("crazy-key", "crazy-value");
        //when
        ResponseEntity<IBuild> responseEntity = restTemplate.postForEntity(addLabelUrl(buildId), newLabels, IBuild.class);

        //then
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(
                responseEntity.getBody().getLabels().stream()
                        .map(IBuildLabel::getValue)
                        .toList()
                        .contains("crazy-value")
        );
    }

    private String searchUrl() {
        return "http://localhost:" + port + "/api/v1/builds/search";
    }

    private String getSingleBuildUrl(Number buildId) {
        return "http://localhost:" + port + "/api/v1/builds/" + buildId;
    }

    private String createUrl() {
        return "http://localhost:" + port + "/api/v1/builds/create";
    }

    private String addLabelUrl(Number buildId) {
        return "http://localhost:" + port + "/api/v1/builds/add-labels/" + buildId;
    }
}
