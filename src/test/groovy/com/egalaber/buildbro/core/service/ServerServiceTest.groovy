package com.egalaber.buildbro.core.service

import com.egalaber.buildbro.BaseBuildBroSpec
import com.egalaber.buildbro.api.fault.DataNotFoundException
import com.egalaber.buildbro.api.model.IServer
import com.egalaber.buildbro.core.domain.Server
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Subject

class ServerServiceTest extends BaseBuildBroSpec {
    @Subject
    @Autowired
    ServerService serverService

    def "all"() {
        expect:
        serverService.all().size() == 3
    }

    def "ByName"() {
        expect:
        serverService.byName("prod-1").get().id == 3
    }

    def "ByName not found"() {
        expect:
        serverService.byName("nosuchserver").isEmpty()
    }

    def "FindOrCreate with existing"() {
        when:
        Server existing = serverService.findOrCreate("prod-1")

        then:
        existing.id == 3
    }

    def "FindOrCreate with new"() {
        when:
        Server created = serverService.findOrCreate("prod-2")

        then:
        created.id
    }

    def "updateServerDetails"() {
        given:
        String newNick = 'Glorious Test-machine'
        String newDescription = 'My private Server'
        IServer server = new IServer()
        server.setId(1L)
        server.setNickName(newNick)
        server.setDescription(newDescription)

        when:
        IServer updated = serverService.updateServerDetails(server)

        then:
        updated.nickName == newNick
        updated.description == newDescription
    }

    def "updateServerDetails"() {
        given:
        String newNick = 'Glorious Test-machine'
        String newDescription = 'My private Server'
        IServer server = new IServer()
        server.setId(-1L)
        server.setNickName(newNick)
        server.setDescription(newDescription)

        when:
        serverService.updateServerDetails(server)

        then:
        thrown(DataNotFoundException)
    }
}
