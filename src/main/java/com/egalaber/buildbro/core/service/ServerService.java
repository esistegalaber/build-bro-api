package com.egalaber.buildbro.core.service;

import com.egalaber.buildbro.api.fault.DataNotFoundException;
import com.egalaber.buildbro.api.model.IServer;
import com.egalaber.buildbro.core.domain.Server;
import com.egalaber.buildbro.core.mapping.ServerMapper;
import com.egalaber.buildbro.core.repository.ServerRepository;
import com.egalaber.buildbro.core.repository.ServerSpecs;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class ServerService {
    private final ServerRepository serverRepository;

    public ServerService(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    public List<IServer> all() {
        return StreamSupport.stream(serverRepository.findAll(Sort.by("id")).spliterator(), false)
                .map(ServerMapper::toApi)
                .toList();
    }

    public Optional<Server> byName(String serverName) {
        return serverRepository.findOne(ServerSpecs.byName(serverName));
    }

    public Server findOrCreate(String serverName) {
        Optional<Server> theServer = byName(serverName);
        return theServer.orElseGet(() -> newServer(serverName));
    }

    private Server newServer(String serverName) {
        Server newServer = new Server();
        newServer.setName(serverName);
        return serverRepository.save(newServer);
    }

    public IServer updateServerDetails(IServer server) throws DataNotFoundException {
        Server theServer = serverRepository.findById(server.getId()).orElseThrow(() ->
                new DataNotFoundException("No server found for id='" + server.getId() + "'"));
        theServer.setNickName(server.getNickName());
        theServer.setDescription(server.getDescription());
        return ServerMapper.toApi(serverRepository.save(theServer));
    }
}
