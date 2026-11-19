package com.egalaber.buildbro.api.v1;

import com.egalaber.buildbro.api.fault.DataNotFoundException;
import com.egalaber.buildbro.api.model.IServer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Servers", description = "Deployment targets. Servers are created automatically by their first deployment.")
public interface ServerEndpoint {

    @Operation(
            operationId = "listServers",
            summary = "List servers")
    @GetMapping("/api/v1/servers")
    List<IServer> allServers();

    @Operation(
            operationId = "updateServer",
            summary = "Update server details",
            description = "Updates nick name and description of the server with the given `id`; its name cannot be changed.")
    @PostMapping("/api/v1/servers")
    IServer updateServerDetails(@RequestBody IServer server) throws DataNotFoundException;
}
