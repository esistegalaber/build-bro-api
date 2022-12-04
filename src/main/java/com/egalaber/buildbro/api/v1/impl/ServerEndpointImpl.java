package com.egalaber.buildbro.api.v1.impl;

import com.egalaber.buildbro.api.model.IServer;
import com.egalaber.buildbro.api.v1.ServerEndpoint;
import com.egalaber.buildbro.core.service.ServerService;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@Transactional
public class ServerEndpointImpl implements ServerEndpoint {
    private final ServerService serverService;

    public ServerEndpointImpl(ServerService serverService) {
        this.serverService = serverService;
    }

    @Override
    public List<IServer> allServers() {
        return serverService.all();
    }
}
