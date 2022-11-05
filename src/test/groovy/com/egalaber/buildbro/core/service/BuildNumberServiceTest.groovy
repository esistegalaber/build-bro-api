package com.egalaber.buildbro.core.service

import com.egalaber.buildbro.BaseBuildBroSpec
import com.egalaber.buildbro.api.model.IBuildNumber
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Subject
import spock.lang.Unroll

class BuildNumberServiceTest extends BaseBuildBroSpec {
    @Subject
    @Autowired
    BuildNumberService buildCountService

    @Unroll("#message")
    def "Next"() {
        when:
        IBuildNumber buildCount = buildCountService.next(project, branch)

        then:
        buildCount.number == expected

        and:
        buildCount.project == project

        and:
        buildCount.branch == branch

        where:
        project             | branch                | expected | message
        'backend'           | 'main'                | 11L      | "Next(): Increments existing build count"
        'backend'           | 'feature/new-feature' | 1L       | "Next(): Creates non-existing build count for new branch"
        'buildz-sub-module' | 'master'              | 1L       | "Next(): Creates non-existing build count for new project"

    }

    @Unroll("#message")
    def "Current"() {
        when:
        IBuildNumber buildCount = buildCountService.current(project, branch)

        then:
        buildCount.number == expected

        and:
        buildCount.project == project

        and:
        buildCount.branch == branch

        where:
        project   | branch    | expected | message
        'backend' | 'main'    | 10L      | "Current(): returns current existing build count"
        'backend' | 'release' | 0L       | "Current(): return non-existing build count with count = 0"
    }

    @Unroll("#message")
    def "Set"() {
        when:
        IBuildNumber buildCount = buildCountService.set(project, branch, 10L)

        then:
        buildCount.number == 10L

        where:
        project             | branch    | message
        'buildz'            | 'master'  | "Set(): sets build number for existing build count"
        'buildz-sub-module' | 'release' | "Set(): creates build number for existing build count"
    }
}