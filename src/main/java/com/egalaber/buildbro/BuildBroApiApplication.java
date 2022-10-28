package com.egalaber.buildbro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class BuildBroApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BuildBroApiApplication.class, args);
    }

}
