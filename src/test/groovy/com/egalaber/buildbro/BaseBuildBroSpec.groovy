package com.egalaber.buildbro

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

/**
 * Created by Josip.Mihelko @ Gmail
 */
@SpringBootTest
@ContextConfiguration
@Transactional
@Sql('/testdata.sql')
abstract class BaseBuildBroSpec extends Specification {
}
