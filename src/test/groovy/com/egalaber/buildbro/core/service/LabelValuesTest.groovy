package com.egalaber.buildbro.core.service

import static com.egalaber.buildbro.api.model.ApiModels.aLabelDefinition

import com.egalaber.buildbro.api.model.LabelTarget
import com.egalaber.buildbro.api.model.LabelType
import com.egalaber.buildbro.api.model.LabelViolationReason
import spock.lang.Specification

class LabelValuesTest extends Specification {

    def "#type #value fits: #fits"() {
        given:
        def definition = aLabelDefinition(key: 'k', type: type, allowedValues: allowedValues, pattern: pattern)

        expect:
        LabelValues.fits(definition, value) == fits

        where:
        type              | allowedValues        | pattern       | value                       || fits
        LabelType.STRING  | null                 | null          | 'anything at all'           || true
        LabelType.STRING  | null                 | null          | ''                          || true
        LabelType.STRING  | null                 | null          | null                        || false
        LabelType.BOOLEAN | null                 | null          | 'true'                      || true
        LabelType.BOOLEAN | null                 | null          | ' FALSE '                   || true
        LabelType.BOOLEAN | null                 | null          | 'True'                      || true
        LabelType.BOOLEAN | null                 | null          | 'yes'                       || false
        LabelType.INTEGER | null                 | null          | '42'                        || true
        LabelType.INTEGER | null                 | null          | ' -7 '                      || true
        LabelType.INTEGER | null                 | null          | '123456789012345678901234'  || true
        LabelType.INTEGER | null                 | null          | '4.2'                       || false
        LabelType.INTEGER | null                 | null          | 'x'                         || false
        LabelType.URL     | null                 | null          | 'https://ci.example.com/1'  || true
        LabelType.URL     | null                 | null          | ' HTTP://ci.example.com '   || true
        LabelType.URL     | null                 | null          | 'ftp://ci.example.com'      || false
        LabelType.URL     | null                 | null          | 'ci.example.com/1'          || false
        LabelType.URL     | null                 | null          | 'http://'                   || false
        LabelType.URL     | null                 | null          | 'http://exa mple.com'       || false
        LabelType.ENUM    | ['passed', 'failed'] | null          | 'passed'                    || true
        LabelType.ENUM    | ['passed', 'failed'] | null          | 'PASSED'                    || true
        LabelType.ENUM    | ['passed', 'failed'] | null          | 'pass'                      || false
        LabelType.REGEX   | null                 | '[0-9a-f]{7}' | 'abc1234'                   || true
        LabelType.REGEX   | null                 | '[0-9a-f]{7}' | 'ABC1234'                   || true
        LabelType.REGEX   | null                 | '[0-9a-f]{7}' | 'abc12345'                  || false
    }

    def "violation of #value on #target: #reason"() {
        given:
        def definition = aLabelDefinition(key: 'qa', type: LabelType.ENUM, appliesTo: [LabelTarget.BUILD], allowedValues: ['passed'])

        expect:
        LabelValues.violation(definition, target, value) == Optional.ofNullable(reason)

        where:
        target                | value    || reason
        LabelTarget.BUILD      | 'Passed' || null
        LabelTarget.BUILD      | 'pass'   || LabelViolationReason.INVALID_VALUE
        LabelTarget.DEPLOYMENT | 'passed' || LabelViolationReason.WRONG_TARGET
        LabelTarget.DEPLOYMENT | 'pass'   || LabelViolationReason.WRONG_TARGET
    }

    def "expectation of #type"() {
        given:
        def definition = aLabelDefinition(key: 'k', type: type, allowedValues: allowedValues, pattern: pattern)

        expect:
        LabelValues.expectation(definition) == expected

        where:
        type              | allowedValues        | pattern || expected
        LabelType.STRING  | null                 | null    || 'any value'
        LabelType.BOOLEAN | null                 | null    || 'true or false'
        LabelType.INTEGER | null                 | null    || 'a whole number'
        LabelType.URL     | null                 | null    || 'an absolute http(s) URL'
        LabelType.ENUM    | ['passed', 'failed'] | null    || 'one of passed, failed'
        LabelType.REGEX   | null                 | '\\d+'  || 'a value matching \\d+'
    }
}
