package com.egalaber.buildbro.api;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface BuildSetEndpoint {

    @GetMapping("/api/v1/build-sets")
    List<String> names();
}
