package com.egalaber.buildbro.api


import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.jdbc.Sql
import spock.lang.Specification

/**
 * Created by Josip.Mihelko @ Gmail
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Sql('/testdata.sql')
class BaseRestSpec extends Specification {
    TestRestTemplate restTemplate = new TestRestTemplate()
    @LocalServerPort
    Integer port

    protected String baseUrl() {
        return "http://localhost:" + port + "/api/v1"
    }
}
