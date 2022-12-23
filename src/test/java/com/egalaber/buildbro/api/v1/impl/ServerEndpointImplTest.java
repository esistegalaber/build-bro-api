package com.egalaber.buildbro.api.v1.impl;

import com.egalaber.buildbro.api.model.IServer;
import com.egalaber.buildbro.api.v1.BaseRestTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

class ServerEndpointImplTest extends BaseRestTest {
    @Test
    public void getAllServers() {
        //given:
        String ALL_SERVERS_URL = baseUrl() + "/servers";
        //when
        ResponseEntity<List> responseEntity = restTemplate.getForEntity(ALL_SERVERS_URL, List.class);

        //then
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        Assertions.assertFalse(responseEntity.getBody().isEmpty());
    }

    @Test
    public void updateServerDetails() {
        //given
        String newNick = "Glorious Test-machine";
        String newDescription = "My private Server";
        IServer server = new IServer();
        server.setId(1L);
        server.setNickName(newNick);
        server.setDescription(newDescription);
        String UPDATE_SERVERS_URL = baseUrl() + "/servers";
        //when
        ResponseEntity<IServer> responseEntity = restTemplate.postForEntity(UPDATE_SERVERS_URL, server, IServer.class);
        //then
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(newNick, responseEntity.getBody().getNickName());
        Assertions.assertEquals(newDescription, responseEntity.getBody().getDescription());
    }
}
