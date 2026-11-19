package com.egalaber.buildbro.core.mapping;

import com.egalaber.buildbro.api.model.IServer;
import com.egalaber.buildbro.core.domain.Server;

public class ServerMapper {
    private ServerMapper() {
    }

    public static IServer toApi(Server server) {
        return new IServer(server.getId(), server.getName(), server.getNickName(), server.getDescription());
    }
}
