package com.egalaber.buildbro.core.service

import static com.egalaber.buildbro.api.model.ApiModels.aLabelDefinition

import com.egalaber.buildbro.BaseBuildBroSpec
import com.egalaber.buildbro.api.fault.InvalidRequestException
import com.egalaber.buildbro.api.model.ILabelDefinition
import com.egalaber.buildbro.api.model.LabelStyle
import com.egalaber.buildbro.api.model.LabelTarget
import com.egalaber.buildbro.api.model.LabelType
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Subject

class LabelDefinitionServiceTest extends BaseBuildBroSpec {
    @Subject
    @Autowired
    LabelDefinitionService labelDefinitionService

    def "all, ordered by key"() {
        expect:
        labelDefinitionService.all()*.key == ['integration-test', 'jenkins-build', 'revision']
    }

    def "byKey ignores case and maps every field"() {
        when:
        ILabelDefinition definition = labelDefinitionService.byKey('Integration-TEST').get()

        then:
        definition.key == 'integration-test'
        definition.displayName == 'Integration test'
        definition.description == 'Outcome of the integration test suite'
        definition.appliesTo == [LabelTarget.BUILD]
        definition.type == LabelType.ENUM
        definition.allowedValues == ['ok', 'broken']
        definition.pattern == null
        definition.style == null
        definition.valueStyles == [ok: LabelStyle.SUCCESS, broken: LabelStyle.ERROR]
    }

    def "byKey not found"() {
        expect:
        labelDefinitionService.byKey('nosuchlabel').isEmpty()
    }

    def "save creates a definition with defaults; the key comes from the argument"() {
        when:
        ILabelDefinition saved = labelDefinitionService.save(' qa ', aLabelDefinition(key: 'ignored'))

        then:
        saved.key == 'qa'
        saved.type == LabelType.STRING
        saved.appliesTo == [LabelTarget.BUILD, LabelTarget.DEPLOYMENT]
        labelDefinitionService.all().size() == 4
    }

    def "save replaces an existing definition, matching the key ignoring case"() {
        given:
        ILabelDefinition replacement = aLabelDefinition(
                type: LabelType.ENUM,
                appliesTo: [LabelTarget.DEPLOYMENT, LabelTarget.BUILD, LabelTarget.BUILD],
                allowedValues: ['OK', 'flaky', 'broken'],
                style: LabelStyle.INFO,
                valueStyles: [OK: LabelStyle.SUCCESS, flaky: LabelStyle.WARNING]
        )

        when:
        labelDefinitionService.save('INTEGRATION-test', replacement)
        ILabelDefinition reloaded = labelDefinitionService.byKey('integration-test').get()

        then:
        labelDefinitionService.all().size() == 3
        reloaded.key == 'INTEGRATION-test'
        reloaded.displayName == null
        reloaded.appliesTo == [LabelTarget.BUILD, LabelTarget.DEPLOYMENT]
        reloaded.allowedValues == ['OK', 'flaky', 'broken']
        reloaded.style == LabelStyle.INFO
        reloaded.valueStyles == [flaky: LabelStyle.WARNING, OK: LabelStyle.SUCCESS]
    }

    def "save rejects an inconsistent definition: #problem"() {
        when:
        labelDefinitionService.save(key, definition)

        then:
        InvalidRequestException e = thrown()
        e.message.contains(problem)

        where:
        key   | definition                                                                                                  || problem
        ' '   | aLabelDefinition()                                                                                          || 'needs a key'
        'qa'  | aLabelDefinition(type: LabelType.ENUM)                                                                      || 'type ENUM needs allowedValues'
        'qa'  | aLabelDefinition(type: LabelType.ENUM, allowedValues: ['ok', ' '])                                         || 'must not be blank'
        'qa'  | aLabelDefinition(type: LabelType.ENUM, allowedValues: ['ok', 'OK'])                                        || "'OK' twice"
        'qa'  | aLabelDefinition(type: LabelType.STRING, allowedValues: ['ok'])                                            || 'only allowed for type ENUM'
        'sha' | aLabelDefinition(type: LabelType.REGEX)                                                                     || 'type REGEX needs a pattern'
        'sha' | aLabelDefinition(type: LabelType.REGEX, pattern: '[0-9')                                                   || 'not a valid regular expression'
        'sha' | aLabelDefinition(type: LabelType.STRING, pattern: '.*')                                                    || 'only allowed for type REGEX'
        'qa'  | aLabelDefinition(type: LabelType.ENUM, allowedValues: ['ok'], valueStyles: [nope: LabelStyle.ERROR])      || "'nope', which is not a valid enum value"
        'qa'  | aLabelDefinition(type: LabelType.BOOLEAN, valueStyles: [True: LabelStyle.SUCCESS, true: LabelStyle.INFO]) || 'twice'
        'qa'  | aLabelDefinition(type: LabelType.BOOLEAN, valueStyles: [maybe: LabelStyle.WARNING])                       || 'not a valid boolean value'
        'qa'  | aLabelDefinition(valueStyles: [ok: null])                                                                  || "no style for 'ok'"
    }

    def "delete removes the definition, ignoring case; unknown keys are ignored"() {
        when:
        labelDefinitionService.delete('REVISION')
        labelDefinitionService.delete('nosuchlabel')

        then:
        labelDefinitionService.all()*.key == ['integration-test', 'jenkins-build']
    }
}
