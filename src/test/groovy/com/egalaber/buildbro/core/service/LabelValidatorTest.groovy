package com.egalaber.buildbro.core.service

import static com.egalaber.buildbro.api.model.ApiModels.aBuild
import static com.egalaber.buildbro.api.model.ApiModels.aBuildLabel
import static com.egalaber.buildbro.api.model.ApiModels.aBuildSetTemplate
import static com.egalaber.buildbro.api.model.ApiModels.aBuildTemplate
import static com.egalaber.buildbro.api.model.ApiModels.aDeployment
import static com.egalaber.buildbro.api.model.ApiModels.aDeploymentLabel
import static com.egalaber.buildbro.api.model.ApiModels.aLabelSettings

import com.egalaber.buildbro.BaseBuildBroSpec
import com.egalaber.buildbro.api.fault.InvalidRequestException
import com.egalaber.buildbro.api.model.IBuild
import com.egalaber.buildbro.api.model.LabelValidation
import org.springframework.beans.factory.annotation.Autowired

/**
 * Label validation on every path that writes labels. The test data defines integration-test (ENUM ok/broken),
 * jenkins-build (URL) and revision (REGEX), all for builds only; part-of-build-set has no definition.
 */
class LabelValidatorTest extends BaseBuildBroSpec {
    @Autowired
    LabelSettingsService labelSettingsService
    @Autowired
    BuildService buildService
    @Autowired
    DeploymentService deploymentService
    @Autowired
    BuildSetTemplateService buildSetTemplateService

    def "Settings default to OFF and can be switched"() {
        expect:
        labelSettingsService.get().labelValidation == LabelValidation.OFF

        when:
        labelSettingsService.save(aLabelSettings(labelValidation: LabelValidation.STRICT))

        then:
        labelSettingsService.get().labelValidation == LabelValidation.STRICT
    }

    def "OFF accepts labels that do not fit"() {
        when:
        IBuild created = buildService.create(newBuild(['integration-test': 'whatever']))

        then:
        created.labels*.value == ['whatever']
    }

    def "STRICT accepts fitting labels, leniently, and labels without a definition"() {
        given:
        strict()

        when:
        IBuild created = buildService.create(newBuild([
                'integration-test' : 'OK',
                'jenkins-build'    : ' https://ci.example.com/job/1 ',
                'revision'         : 'B6F670F21C57A56A1A4717E3DF5F196E25A1A3C4EF3E66877F9BEE9F2DEBF50F',
                'part-of-build-set': 'anything'
        ]))

        then:
        created.labels.size() == 4
    }

    def "STRICT rejects a new build with labels that do not fit, naming every problem"() {
        given:
        strict()

        when:
        buildService.create(newBuild(['integration-test': 'flaky', 'jenkins-build': 'not a url']))

        then:
        InvalidRequestException e = thrown()
        e.message == "Label 'integration-test=flaky' does not fit its definition: expected one of ok, broken; " +
                "Label 'jenkins-build=not a url' does not fit its definition: expected an absolute http(s) URL"
    }

    def "STRICT rejects labels added to a build"() {
        given:
        strict()

        when:
        buildService.addLabels(1L, ['revision': 'abc'])

        then:
        InvalidRequestException e = thrown()
        e.message == "Label 'revision=abc' does not fit its definition: expected a value matching [0-9a-f]{64}"
    }

    def "STRICT rejects deployment labels whose definition is meant for builds"() {
        given:
        strict()

        when:
        deploymentService.create(aDeployment(serverName: 'strict-server', builds: [aBuild(id: 1)],
                labels: [aDeploymentLabel(key: 'Integration-Test', value: 'ok')]))

        then:
        InvalidRequestException e = thrown()
        e.message == "Label 'Integration-Test' is not meant for deployments"
    }

    def "STRICT rejects labels added to the builds of a deployment"() {
        given:
        strict()

        when:
        deploymentService.addLabelsToBuilds(1L, [aBuildLabel(key: 'integration-test', value: 'meh')])

        then:
        thrown(InvalidRequestException)
    }

    def "STRICT rejects build-set templates matching on a label value that cannot exist"() {
        given:
        strict()

        when:
        buildSetTemplateService.save(aBuildSetTemplate(name: 'strict-set', buildTemplates: [
                aBuildTemplate(project: 'backend', branch: 'main', labels: ['integration-test': 'passed'])
        ]))

        then:
        InvalidRequestException e = thrown()
        e.message.startsWith("Label 'integration-test=passed' does not fit")
    }

    private void strict() {
        labelSettingsService.save(aLabelSettings(labelValidation: LabelValidation.STRICT))
    }

    private static IBuild newBuild(Map<String, String> labels) {
        aBuild(project: 'strict', branch: 'main', buildNumber: 1,
                labels: labels.collect { key, value -> aBuildLabel(key: key, value: value) })
    }
}
