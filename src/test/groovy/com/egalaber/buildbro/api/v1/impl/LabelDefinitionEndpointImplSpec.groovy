package com.egalaber.buildbro.api.v1.impl

import com.egalaber.buildbro.api.BaseRestSpec
import com.egalaber.buildbro.api.model.IExceptionInfo
import com.egalaber.buildbro.api.model.ILabelDefinition
import com.egalaber.buildbro.api.model.LabelStyle
import com.egalaber.buildbro.api.model.LabelTarget
import com.egalaber.buildbro.api.model.LabelType
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
        ResponseEntity<ILabelDefinition> afterCreate = put(url, created)
        ResponseEntity<ILabelDefinition> afterReplace = put(url, replaced)
        restTemplate.delete(url)

        then:
        afterCreate.statusCode == HttpStatus.OK
        afterCreate.body.type == LabelType.ENUM
        afterCreate.body.appliesTo == [LabelTarget.BUILD]
        afterCreate.body.valueStyles == [passed: LabelStyle.SUCCESS, pending: LabelStyle.WARNING, failed: LabelStyle.ERROR]

        and:
        afterReplace.statusCode == HttpStatus.OK
        afterReplace.body.type == LabelType.BOOLEAN
        afterReplace.body.displayName == 'QA passed'
        afterReplace.body.allowedValues == []
        afterReplace.body.appliesTo == [LabelTarget.BUILD, LabelTarget.DEPLOYMENT]

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

    private ResponseEntity<ILabelDefinition> put(String url, Map body) {
        restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(body), ILabelDefinition)
    }
}
