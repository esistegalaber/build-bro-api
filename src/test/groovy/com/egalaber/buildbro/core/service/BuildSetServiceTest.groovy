package com.egalaber.buildbro.core.service

import static com.egalaber.buildbro.api.model.ApiModels.*

import com.egalaber.buildbro.BaseBuildBroSpec
import com.egalaber.buildbro.api.fault.InvalidRequestException
import com.egalaber.buildbro.api.model.IBuildSetTemplate
import com.egalaber.buildbro.api.model.IBuildTemplate
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Subject
import spock.lang.Unroll

/**
 * Created by Josip.Mihelko @ Gmail
 */
class BuildSetServiceTest extends BaseBuildBroSpec {
    @Subject
    @Autowired
    BuildSetTemplateService service

    @Unroll("#message")
    def "ByName"() {
        when:
        Optional<IBuildSetTemplate> env = service.byName(name)

        then:
        env.isPresent() == expected

        where:
        name      | expected | message
        'main'    | true     | 'ByName(): finds existing Environment by name'
        'missing' | false    | 'ByName(): is empty for non-existing environment name'
    }

    def "Save inserting new instance"() {
        given:
        IBuildTemplate artifact = aBuildTemplate(
                project: 'some-project',
                branch: 'some-branch',
                labels: ['some': 'label']
        )
        IBuildSetTemplate buildSetTemplate = aBuildSetTemplate(
                name: 'new-buildSetTemplate',
                buildTemplates: [artifact]
        )

        when:
        IBuildSetTemplate inserted = service.save(buildSetTemplate)

        then:
        inserted.id

        and:
        inserted.buildTemplates().size() == 1

        and:
        !inserted.buildTemplates().first().labels().isEmpty()
    }


    def "Save updating existing instance"() {
        given:
        IBuildTemplate artifact = aBuildTemplate(
                project: 'some-project',
                branch: 'some-branch',
                labels: ['some': 'label']
        )
        IBuildSetTemplate environment = aBuildSetTemplate(
                id: 1,
                name: 'main',
                buildTemplates: [artifact]
        )


        when:
        IBuildSetTemplate inserted = service.save(environment)

        then:
        inserted.id
    }

    def "Save throwing Exception on duplicate Name"() {
        IBuildSetTemplate environment = aBuildSetTemplate(name: 'main')

        when:
        service.save(environment)

        then:
        thrown(InvalidRequestException)
    }

    def "Delete"() {
        expect:
        service.delete('main')
    }

    def "List"() {
        expect:
        !service.allBuildSetTemplateNames().isEmpty()
    }

    def "Attempts to update an Environment with an invalid ID"() {
        service.save(aBuildSetTemplate(name: 'testing'))

        when:
        service.save(aBuildSetTemplate(id: -1L, name: 'testing (update)'))

        then:
        thrown(InvalidRequestException)
    }
}
