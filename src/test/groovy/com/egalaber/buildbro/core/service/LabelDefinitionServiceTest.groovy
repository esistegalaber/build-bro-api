package com.egalaber.buildbro.core.service

import static com.egalaber.buildbro.api.model.ApiModels.aLabelDefinition

import com.egalaber.buildbro.BaseBuildBroSpec
import com.egalaber.buildbro.api.fault.InvalidRequestException
import com.egalaber.buildbro.api.model.ILabelDefinition
import com.egalaber.buildbro.api.model.ILabelDefinitionChange
import com.egalaber.buildbro.api.model.ILabelViolation
import com.egalaber.buildbro.api.model.LabelStyle
import com.egalaber.buildbro.api.model.LabelTarget
import com.egalaber.buildbro.api.model.LabelType
import com.egalaber.buildbro.api.model.LabelViolationReason
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
        ILabelDefinition saved = labelDefinitionService.save(' qa ', aLabelDefinition(key: 'ignored'), false).definition()

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
        labelDefinitionService.save('INTEGRATION-test', replacement, false)
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
        labelDefinitionService.save(key, definition, false)

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

    def "violations: the test data fits its definitions"() {
        expect:
        labelDefinitionService.violations() == []
    }

    def "violations: invalid values per key and value, wrong targets per key; values compared ignoring case"() {
        given: "'broken' no longer allowed, and 'part-of-build-set' defined for builds only"
        labelDefinitionService.save('integration-test', aLabelDefinition(type: LabelType.ENUM, appliesTo: [LabelTarget.BUILD], allowedValues: ['OK']), false)
        labelDefinitionService.save('part-of-build-set', aLabelDefinition(appliesTo: [LabelTarget.BUILD]), false)

        when:
        List<ILabelViolation> violations = labelDefinitionService.violations()

        then: 'the five part-of-build-set labels sit on one deployment'
        violations == [
                new ILabelViolation('integration-test', 'broken', LabelTarget.BUILD, LabelViolationReason.INVALID_VALUE, 45L),
                new ILabelViolation('part-of-build-set', null, LabelTarget.DEPLOYMENT, LabelViolationReason.WRONG_TARGET, 1L)
        ]
    }

    def "save reports the stored labels that do not fit the new definition"() {
        when:
        ILabelDefinitionChange change = labelDefinitionService.save('part-of-build-set',
                aLabelDefinition(type: LabelType.ENUM, appliesTo: [LabelTarget.DEPLOYMENT], allowedValues: ['MAIN']), false)

        then:
        change.definition().key == 'part-of-build-set'
        change.violations() == [new ILabelViolation('part-of-build-set', 'feature', LabelTarget.DEPLOYMENT, LabelViolationReason.INVALID_VALUE, 1L)]
        labelDefinitionService.byKey('part-of-build-set').isPresent()
    }

    def "save with dryRun reports, but saves nothing"() {
        when:
        ILabelDefinitionChange change = labelDefinitionService.save('revision',
                aLabelDefinition(type: LabelType.REGEX, appliesTo: [LabelTarget.BUILD], pattern: '[0-9a-f]{7}'), true)

        then:
        change.definition().pattern == '[0-9a-f]{7}'
        change.violations().size() == 90
        change.violations().every { it.key == 'revision' && it.reason == LabelViolationReason.INVALID_VALUE && it.count == 1L }

        and:
        labelDefinitionService.byKey('revision').get().pattern == '[0-9a-f]{64}'
    }

    def "save with dryRun still rejects an inconsistent definition"() {
        when:
        labelDefinitionService.save('revision', aLabelDefinition(type: LabelType.REGEX), true)

        then:
        thrown(InvalidRequestException)
    }
}
