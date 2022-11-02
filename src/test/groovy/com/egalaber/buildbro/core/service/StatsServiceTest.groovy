package com.egalaber.buildbro.core.service


import com.egalaber.buildbro.BaseBuildBroSpec
import com.egalaber.buildbro.api.model.IBuildBroStats
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Subject

/**
 * Created by Josip.Mihelko @ Gmail
 */
class StatsServiceTest extends BaseBuildBroSpec {

    @Subject
    @Autowired
    StatsService statesService

    def "Stats"() {
        when:
        IBuildBroStats stats = statesService.stats()

        then:
        stats.numberOfBuilds > 0
    }
}
