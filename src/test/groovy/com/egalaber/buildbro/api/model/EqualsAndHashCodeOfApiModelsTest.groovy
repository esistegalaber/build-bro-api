package com.egalaber.buildbro.api.model

import com.egalaber.buildbro.core.domain.*
import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

class EqualsAndHashCodeOfApiModelsTest extends Specification {

    def "Equals and hashCode of IBuild"() {
        expect:
        EqualsVerifier.simple().forClass(IBuild)
                .usingGetClass()
                .verify()
    }

    def "Equals and hashCode of IBuildNumber"() {
        expect:
        EqualsVerifier.simple().forClass(IBuildNumber)
                .usingGetClass()
                .verify()
    }

    def "Equals and hashCode of IBuildLabel"() {
        expect:
        EqualsVerifier.simple().forClass(IBuildLabel)
                .usingGetClass()
                .verify()
    }

    def "Equals and hashCode of IBuildBroStats"() {
        expect:
        EqualsVerifier.simple().forClass(IBuildBroStats)
                .usingGetClass()
                .verify()
    }

    def "Equals and hashCode of IProject"() {
        expect:
        EqualsVerifier.simple().forClass(IProject)
                .usingGetClass()
                .verify()
    }

    def "Equals and hashCode of IBranch"() {
        expect:
        EqualsVerifier.simple().forClass(IBranch)
                .usingGetClass()
                .verify()
    }

    def "Equals and hashCode of IServer"() {
        expect:
        EqualsVerifier.simple().forClass(IServer)
                .usingGetClass()
                .verify()
    }

    def "Equals and hashCode of IDeployment"() {
        expect:
        EqualsVerifier.simple().forClass(IDeployment)
                .usingGetClass()
                .verify()
    }

    def "Equals and hashCode of IDeploymentLabel"() {
        expect:
        EqualsVerifier.simple().forClass(IDeploymentLabel)
                .usingGetClass()
                .verify()
    }
}
