package com.egalaber.buildbro;

import com.egalaber.buildbro.core.aop.LoggingAspect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@Import({LoggingAspect.class})
public class BuildBroApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BuildBroApiApplication.class, args);
    }

}
