package com.egalaber.buildbro.api.v1.impl;

import com.egalaber.buildbro.api.model.IBuildBroStats;
import com.egalaber.buildbro.api.v1.BaseRestTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StatsEndpointImplTest extends BaseRestTest {
    @Test
    void testGetStats() {
        String STATS_URL = baseUrl() + "/stats";
        ResponseEntity<IBuildBroStats> responseEntity = restTemplate.getForEntity(STATS_URL, IBuildBroStats.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
