package com.egalaber.buildbro.api;

import com.egalaber.buildbro.api.fault.DataNotFoundException;
import com.egalaber.buildbro.api.fault.InvalidRequestException;
import com.egalaber.buildbro.api.model.IBuildSet;
import com.egalaber.buildbro.api.model.IBuildSetTemplate;
import com.egalaber.buildbro.api.model.IBuildTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

public interface BuildSetEndpoint {

    @GetMapping("/api/v1/build-sets/names")
    List<String> names();

    @PostMapping(value = "/api/v1/build-sets")
    IBuildSetTemplate save(@RequestBody IBuildSetTemplate environment) throws InvalidRequestException;

    @GetMapping("/api/v1/build-sets/{name}")
    IBuildSetTemplate get(@PathVariable("name") String name) throws DataNotFoundException;

    @PostMapping(value = "/api/v1/build-sets/verify")
    IBuildSet verify(@RequestBody Set<IBuildTemplate> artifacts);

    @GetMapping("/api/v1/build-sets/of/{name}")
    IBuildSet buildsOf(@PathVariable(name = "name") String name) throws DataNotFoundException;

    @DeleteMapping("/api/v1/build-sets/{name}")
    void delete(@PathVariable("name") String name);
}
