package com.egalaber.buildbro.api.v1;

import com.egalaber.buildbro.api.model.IServer;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface ServerEndpoint {

    @GetMapping("/api/v1/servers")
    List<IServer> allServers();

}
