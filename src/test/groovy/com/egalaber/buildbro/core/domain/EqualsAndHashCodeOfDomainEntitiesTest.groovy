package com.egalaber.buildbro.core.domain


import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

class EqualsAndHashCodeOfDomainEntitiesTest extends Specification {

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

    def "Equals and hashCode of BuildLabel"() {
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

    def "Equals and hashCode of BuildSetTemplate"() {
        expect:
        EqualsVerifier.forClass(BuildSetTemplate)
                .usingGetClass()
                .verify()
    }

    def "Equals and hashCode of BuildTemplate"() {
        expect:
        EqualsVerifier.forClass(BuildTemplate)
                .usingGetClass()
                .verify()
    }

    def "Equals and hashCode of Deployment"() {
        expect:
        EqualsVerifier.forClass(Deployment)
                .withIgnoredFields("labels", "builds")
                .withPrefabValues(Build, new Build(project: 'first'), new Build(project: 'second'))
                .withPrefabValues(DeploymentLabel, new DeploymentLabel(key: 'first'), new DeploymentLabel(key: 'second'))
                .withIgnoredFields('builds', 'labels')
                .usingGetClass()
                .verify()
    }

    def "Equals and hashCode of DeploymentLabel"() {
        expect:
        EqualsVerifier.forClass(DeploymentLabel)
                .withPrefabValues(Deployment, new Deployment(server: new Server(name: 'first')), new Deployment(server: new Server(name: 'second')))
                .usingGetClass()
                .verify()
    }

    def "Equals and hashCode of Server"() {
        expect:
        EqualsVerifier.forClass(Server)
                .usingGetClass()
                .verify()
    }
}
