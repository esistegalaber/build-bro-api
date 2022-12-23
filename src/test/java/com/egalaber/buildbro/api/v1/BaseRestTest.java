package com.egalaber.buildbro.api.v1;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Sql("/testdata.sql")
public abstract class BaseRestTest {
    protected TestRestTemplate restTemplate = new TestRestTemplate();
    @LocalServerPort
    protected Integer port;

    protected String baseUrl() {
        return "http://localhost:" + port + "/api/v1";
    }
}
