package com.egalaber.buildbro.api.v1;

import com.egalaber.buildbro.api.fault.DataNotFoundException;
import com.egalaber.buildbro.api.model.IServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ServerEndpoint {

    @GetMapping("/api/v1/servers")
    List<IServer> allServers();

    @PostMapping("/api/v1/servers")
    IServer updateServerDetails(@RequestBody IServer server) throws DataNotFoundException;

}
