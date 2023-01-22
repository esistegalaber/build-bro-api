package com.egalaber.buildbro.api.v1.impl

import com.egalaber.buildbro.api.BaseRestSpec
import com.egalaber.buildbro.api.model.IServer
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class ServerEndpointImplSpec extends BaseRestSpec {
    def "AllServers"() {
        given:
        String ALL_SERVERS_URL = "${baseUrl()}/servers"

        when:
        ResponseEntity<IServer[]> response = restTemplate.getForEntity(ALL_SERVERS_URL, IServer[])

        then:
        response.getStatusCode() == HttpStatus.OK

        and:
        response.getBody().length > 0

    }

    def "UpdateServerDetails"() {
        given:
        String newNick = 'Glorious Test-machine'
        String newDescription = 'My private Server'
        IServer server = new IServer()
        server.setId(1L)
        server.setNickName(newNick)
        server.setDescription(newDescription)
        String UPDATE_SERVERS_URL = "${baseUrl()}/servers"

        when:
        ResponseEntity<IServer> response = restTemplate.postForEntity(UPDATE_SERVERS_URL, server, IServer)

        then:
        response.getStatusCode() == HttpStatus.OK

    }
}
