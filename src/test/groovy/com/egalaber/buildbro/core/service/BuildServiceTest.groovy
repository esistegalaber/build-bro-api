package com.egalaber.buildbro.core.service

import com.egalaber.buildbro.BaseBuildBroSpec
import com.egalaber.buildbro.api.fault.DataNotFoundException
import com.egalaber.buildbro.api.model.IBuild
import com.egalaber.buildbro.api.model.IBuildSearch
import com.egalaber.buildbro.api.model.IBuildSet
import com.egalaber.buildbro.api.model.IBuildTemplate
import com.egalaber.buildbro.core.domain.Build
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import spock.lang.Subject
import spock.lang.Unroll

class BuildServiceTest extends BaseBuildBroSpec {
    @Subject
    @Autowired
    BuildService service

    @Unroll("#message")
    def "ById"() {
        when:
        Optional<IBuild> build = service.byId(id)

        then:
        build.isPresent() == expected

        where:
        id  | expected | message
        1L  | true     | 'ById finds existing Build'
        -1L | false    | 'ById is empty for missing Build'

    }

    def "Create"() {
        expect:
        service.create(
                new IBuild(
                        project: 'backend',
                        branch: 'next',
                        buildNumber: 1
                )).id
    }

    def "AddLabels"() {
        given:
        Map<String, String> labels = [:]
        labels.put('newKey', 'newValue')

        when:
        IBuild theBuild = service.addLabels(1L, labels)

        then:
        theBuild.labels.size() == 4

    }

    def "AddLabels with wrong build id"() {
        given:
        Map<String, String> labels = [:]
        labels.put('newKey', 'newValue')

        when:
        service.addLabels(-1L, labels)

        then:
        thrown(DataNotFoundException)
    }

    @Unroll("#message")
    def "Search"() {
        given:
        IBuildSearch buildSearch = new IBuildSearch(
                project: theProject,
                branch: theBranch,
                minBuildNumber: theMinBuildNumber,
                maxBuildNumber: theMaxBuildNumber,
                pageSize: 100,
                page: 0
        )
        if (theLabelKey && theLabelValue) {
            buildSearch.labels[theLabelKey] = theLabelValue
        }
        when:
        Page<Build> result = service.search(buildSearch)

        then:
        result.totalElements == expected

        and:
        if (theProject) {
            result.getContent().project.every {
                it == theProject
            }
        }

        and:
        if (theBranch) {
            result.getContent().branch.every {
                it == theBranch
            }
        }


        where:
        theProject | theBranch | theLabelKey        | theLabelValue  | expected | theMinBuildNumber | theMaxBuildNumber | message
        null       | null      | null               | null           | 90       | null              | null              | "Search(): finds all builds for empty search"
        null       | null      | null               | null           | 9        | 1                 | 3                 | "Search(): finds all builds with build number = 2"
        'backend'  | null      | null               | null           | 30       | null              | null              | "Search(): finds all builds of project 'backend'"
        'backend'  | 'main'    | null               | null           | 10       | null              | null              | "Search(): finds all builds of project 'backend' of branch 'master"
        'backend'  | 'main'    | 'integration-test' | 'ok'           | 5        | null              | null              | "Search(): finds all builds of projects with label technical_branch=feature/some-other-feature"
        null       | null      | 'technical_branch' | 'noSuchBranch' | 0        | null              | null              | "Search(): is empty for empty label sub-search"
    }

    @Unroll("#message")
    def "Latest()"() {
        IBuildTemplate buildTemplate = new IBuildTemplate(
                project: theProject,
                branch: theBranch
        )
        if (theLabelKey && theLabelValue) {
            buildTemplate.getLabels().put(theLabelKey, theLabelValue)
        }
        when:
        Optional<IBuild> latest = service.withBuildNumberOrLatest(buildTemplate)

        then:
        if (expectedBuildNumber) {
            latest.isPresent()
            latest.get().getBuildNumber() == expectedBuildNumber
        } else {
            !latest.isPresent()
        }

        where:
        theProject | theBranch        | theLabelKey        | theLabelValue | expectedBuildNumber | message
        'backend'  | 'main'           | null               | null          | 10L                 | "withBuildNumberOrLatest(): finds latest build of branch master"
        'backend'  | 'main'           | 'integration-test' | 'ok'          | 9L                  | "withBuildNumberOrLatest(): finds latest build for a specific label"
        'backend'  | null             | 'doesnot'          | 'exist'       | null                | "withBuildNumberOrLatest(): is empty for missing labels"
        'backend'  | 'deleted-branch' | null               | null          | null                | "withBuildNumberOrLatest(): is empty for missing project/branch combo"
    }

    @Unroll("#message")
    def "buildsOf an BuildSetTemplate"() {
        when:
        IBuildSet builds = service.buildsOf(envName)

        then:
        builds.builds.size() == expectedSize

        where:
        envName                | expectedSize | message
        'main'                 | 2            | "buildsOf(): returns 2 builds for well - defined Environment (main)"
        'feature-test-stage-1' | 0            | "buildsOf(): returns 0 builds for un-defined Environment (feature-test-stage-1)"
    }

    @Unroll("#message")
    def "buildsOf an Set of BuildTemplates"() {
        when:
        IBuildSet builds = service.buildsOf(templates)

        then:
        builds.builds.size() == expectedSize

        where:
        templates                                                                                                                | expectedSize | message
        [new IBuildTemplate(project: 'backend', branch: 'main')] as Set                                                          | 1            | "buildsOf(): returns 1 builds for well-defined single BuildTemplate"
        [new IBuildTemplate(project: 'backend', branch: 'main'), new IBuildTemplate(project: 'frontend', branch: 'main')] as Set | 2            | "buildsOf(): returns 2 builds for 2 well-defined BuildTemplates"
        [new IBuildTemplate(project: 'missing', branch: 'main')] as Set                                                          | 0            | "buildsOf(): returns 0 builds for un-defined BuildTemplate"
    }

    def "OfEnvironment with wrong env name"() {
        when:
        service.buildsOf('no-such-env')

        then:
        thrown(DataNotFoundException)
    }
}
