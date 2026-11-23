package com.egalaber.buildbro.api.v1.impl;

import com.egalaber.buildbro.api.model.IServer;
import com.egalaber.buildbro.api.v1.ServersApi;
import com.egalaber.buildbro.core.service.ServerService;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Implements {@link ServersApi}, which is generated from the contract ({@code openapi/build-bro-api.yaml}).
 */
@RestController
@Transactional
public class ServerEndpointImpl implements ServersApi {
    private final ServerService serverService;

    public ServerEndpointImpl(ServerService serverService) {
        this.serverService = serverService;
    }

    @Override
    public List<IServer> listServers() {
        return serverService.all();
    }

    @Override
    public IServer updateServer(IServer server) {
        return serverService.updateServerDetails(server);
    }
}
