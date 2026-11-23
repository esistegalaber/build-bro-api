package com.egalaber.buildbro.api.v1.impl

import com.egalaber.buildbro.api.BaseRestSpec
import com.egalaber.buildbro.api.model.IExceptionInfo
import com.egalaber.buildbro.api.model.IBuild
import com.egalaber.buildbro.api.model.ILabelDefinition
import com.egalaber.buildbro.api.model.ILabelDefinitionChange
import com.egalaber.buildbro.api.model.ILabelSettings
import com.egalaber.buildbro.api.model.ILabelViolation
import com.egalaber.buildbro.api.model.LabelStyle
import com.egalaber.buildbro.api.model.LabelTarget
import com.egalaber.buildbro.api.model.LabelType
import com.egalaber.buildbro.api.model.LabelValidation
import com.egalaber.buildbro.api.model.LabelViolationReason
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class LabelDefinitionEndpointImplSpec extends BaseRestSpec {

    def "List"() {
        when:
        ResponseEntity<ILabelDefinition[]> response = restTemplate.getForEntity("${baseUrl()}/label-definitions", ILabelDefinition[])

        then:
        response.statusCode == HttpStatus.OK
        response.body*.key == ['integration-test', 'jenkins-build', 'revision']
    }

    def "Get, ignoring the case of the key"() {
        when:
        ResponseEntity<ILabelDefinition> response = restTemplate.getForEntity("${baseUrl()}/label-definitions/Jenkins-Build", ILabelDefinition)

        then:
        response.statusCode == HttpStatus.OK
        response.body.key == 'jenkins-build'
        response.body.type == LabelType.URL
        response.body.style == LabelStyle.INFO
    }

    def "Get DataNotFound"() {
        when:
        ResponseEntity<IExceptionInfo> response = restTemplate.getForEntity("${baseUrl()}/label-definitions/nosuchlabel", IExceptionInfo)

        then:
        response.statusCode == HttpStatus.NOT_FOUND
        response.body.key == IExceptionInfo.KeyEnum.NOT_FOUND
        response.body.message == "No label definition found for key='nosuchlabel'"
    }

    def "Create, replace and delete; enums are accepted in any case"() {
        given:
        String url = "${baseUrl()}/label-definitions/qa"
        Map created = [type: 'enum', appliesTo: ['build'], allowedValues: ['passed', 'pending', 'failed'],
                       valueStyles: [passed: 'success', pending: 'Warning', failed: 'ERROR']]
        Map replaced = [type: 'BOOLEAN', displayName: 'QA passed', valueStyles: [true: 'SUCCESS', false: 'ERROR']]

        when:
        ResponseEntity<ILabelDefinitionChange> afterCreate = put(url, created)
        ResponseEntity<ILabelDefinitionChange> afterReplace = put(url, replaced)
        restTemplate.delete(url)

        then:
        afterCreate.statusCode == HttpStatus.OK
        afterCreate.body.definition.type == LabelType.ENUM
        afterCreate.body.definition.appliesTo == [LabelTarget.BUILD]
        afterCreate.body.definition.valueStyles == [passed: LabelStyle.SUCCESS, pending: LabelStyle.WARNING, failed: LabelStyle.ERROR]
        afterCreate.body.violations == []

        and:
        afterReplace.statusCode == HttpStatus.OK
        afterReplace.body.definition.type == LabelType.BOOLEAN
        afterReplace.body.definition.displayName == 'QA passed'
        afterReplace.body.definition.allowedValues == []
        afterReplace.body.definition.appliesTo == [LabelTarget.BUILD, LabelTarget.DEPLOYMENT]

        and:
        restTemplate.getForEntity(url, IExceptionInfo).statusCode == HttpStatus.NOT_FOUND
    }

    def "Save InvalidRequest"() {
        when:
        ResponseEntity<IExceptionInfo> response = restTemplate.exchange(
                "${baseUrl()}/label-definitions/qa", HttpMethod.PUT, new HttpEntity<>([type: 'ENUM']), IExceptionInfo)

        then:
        response.statusCode == HttpStatus.BAD_REQUEST
        response.body.key == IExceptionInfo.KeyEnum.INVALID_REQUEST
        response.body.message == "Label definition 'qa': type ENUM needs allowedValues"
    }

    def "Delete of an unknown key does nothing"() {
        when:
        ResponseEntity<Void> response = restTemplate.exchange("${baseUrl()}/label-definitions/nosuchlabel", HttpMethod.DELETE, null, Void)

        then:
        response.statusCode == HttpStatus.OK
    }

    def "Save with dryRun reports violations and saves nothing"() {
        when:
        ResponseEntity<ILabelDefinitionChange> response = put("${baseUrl()}/label-definitions/integration-test?dryRun=true",
                [type: 'ENUM', appliesTo: ['BUILD'], allowedValues: ['ok']])

        then:
        response.statusCode == HttpStatus.OK
        response.body.violations == [new ILabelViolation('integration-test', 'broken', LabelTarget.BUILD, LabelViolationReason.INVALID_VALUE, 45L)]

        and:
        restTemplate.getForEntity("${baseUrl()}/label-definitions/integration-test", ILabelDefinition).body.allowedValues == ['ok', 'broken']
    }

    def "Violations"() {
        given:
        put("${baseUrl()}/label-definitions/part-of-build-set", [appliesTo: ['BUILD']])

        when:
        ResponseEntity<ILabelViolation[]> response = restTemplate.getForEntity("${baseUrl()}/label-violations", ILabelViolation[])

        then:
        response.statusCode == HttpStatus.OK
        response.body as List == [new ILabelViolation('part-of-build-set', null, LabelTarget.DEPLOYMENT, LabelViolationReason.WRONG_TARGET, 1L)]
    }

    def "Settings: STRICT rejects a build whose label does not fit"() {
        given:
        String settingsUrl = "${baseUrl()}/label-settings"

        when:
        ILabelSettings before = restTemplate.getForEntity(settingsUrl, ILabelSettings).body
        ResponseEntity<ILabelSettings> switched = restTemplate.exchange(settingsUrl, HttpMethod.PUT,
                new HttpEntity<>([labelValidation: 'strict']), ILabelSettings)
        ResponseEntity<IExceptionInfo> rejected = restTemplate.postForEntity("${baseUrl()}/builds/create",
                [project: 'strict', branch: 'main', buildNumber: 1, labels: [[key: 'integration-test', value: 'flaky']]], IExceptionInfo)
        ResponseEntity<IBuild> accepted = restTemplate.postForEntity("${baseUrl()}/builds/create",
                [project: 'strict', branch: 'main', buildNumber: 2, labels: [[key: 'integration-test', value: 'OK']]], IBuild)

        then:
        before.labelValidation == LabelValidation.OFF
        switched.body.labelValidation == LabelValidation.STRICT

        and:
        rejected.statusCode == HttpStatus.BAD_REQUEST
        rejected.body.message == "Label 'integration-test=flaky' does not fit its definition: expected one of ok, broken"

        and:
        accepted.statusCode == HttpStatus.OK
    }

    private ResponseEntity<ILabelDefinitionChange> put(String url, Map body) {
        restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(body), ILabelDefinitionChange)
    }
}
