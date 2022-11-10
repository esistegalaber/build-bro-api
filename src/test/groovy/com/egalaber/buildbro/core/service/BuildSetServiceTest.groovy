package com.egalaber.buildbro.core.service

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
        IBuildSetTemplate buildSetTemplate = new IBuildSetTemplate()
        buildSetTemplate.setName("new-buildSetTemplate")
        IBuildTemplate artifact = new IBuildTemplate(
                project: 'some-project',
                branch: 'some-branch'
        )
        artifact.getLabels().put('some', 'label')
        buildSetTemplate.getBuildTemplates().add(artifact)

        when:
        IBuildSetTemplate inserted = service.save(buildSetTemplate)

        then:
        inserted.id

        and:
        inserted.getBuildTemplates().size() == 1

        and:
        !inserted.getBuildTemplates().first().getLabels().isEmpty()
    }


    def "Save updating existing instance"() {
        given:
        IBuildSetTemplate environment = new IBuildSetTemplate(
                id: 1,
                name: 'main'
        )
        IBuildTemplate artifact = new IBuildTemplate(
                project: 'some-project',
                branch: 'some-branch'
        )
        artifact.getLabels().put('some', 'label')
        environment.getBuildTemplates().add(artifact)


        when:
        IBuildSetTemplate inserted = service.save(environment)

        then:
        inserted.id
    }

    def "Save throwing Exception on duplicate Name"() {
        IBuildSetTemplate environment = new IBuildSetTemplate()
        environment.setName("main")

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
        IBuildSetTemplate environment = new IBuildSetTemplate()
        environment.setName("testing")
        service.save(environment)

        environment.setId(-1L)

        when:
        environment.setName("testing (update)")
        service.save(environment)

        then:
        thrown(InvalidRequestException)
    }
}
