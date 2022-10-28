package com.egalaber.buildbro.core.domain

import com.egalaber.buildbro.core.repository.ProjectRepository
import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

class EqualsAndHashCodeTest extends Specification {

    def "Equals and hashCode of Build"() {
        expect:
        EqualsVerifier.forClass(Build)
                .withPrefabValues(BuildLabel, new BuildLabel(key: 'first'), new BuildLabel(key: 'second'))
                .withIgnoredFields("labels")
                .usingGetClass()
                .verify()
    }

    def "Equals and hashCode of BuildNumber"() {
        expect:
        EqualsVerifier.forClass(BuildNumber)
                .usingGetClass()
                .verify()
    }

    def "Equals and hashCode of BuildNumber"() {
        expect:
        EqualsVerifier.forClass(BuildLabel)
                .withPrefabValues(Build, new Build(project: 'first'), new Build(project: 'second'))
                .usingGetClass()
                .verify()
    }

    def "Equals and hashCode of Project"() {
        expect:
        EqualsVerifier.forClass(Project)
                .withPrefabValues(Branch, new Branch(name: 'main'), new Branch(name: 'dev'))
                .withIgnoredFields('branches')
                .usingGetClass()
                .verify()
    }

    def "Equals and hashCode of Branch"() {
        expect:
        EqualsVerifier.forClass(Branch)
                .withPrefabValues(Project, new Project(name: 'main'), new Project(name: 'dev'))
                .usingGetClass()
                .verify()
    }
}
