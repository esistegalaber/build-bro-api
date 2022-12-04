package com.egalaber.buildbro.core.mapping;

import com.egalaber.buildbro.api.model.IServer;
import com.egalaber.buildbro.core.domain.Server;

public class ServerMapper {
    private ServerMapper() {
    }

    public static IServer toApi(Server server) {
        IServer toReturn = new IServer();
        toReturn.setId(server.getId());
        toReturn.setName(server.getName());
        toReturn.setNickName(server.getNickName());
        toReturn.setDescription(server.getDescription());
        return toReturn;
    }
}
