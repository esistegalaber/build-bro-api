package com.egalaber.buildbro.api;

import com.egalaber.buildbro.api.fault.DataNotFoundException;
import com.egalaber.buildbro.api.model.IBuildSearch;
import com.egalaber.buildbro.api.model.IBuildSearchResult;
import com.egalaber.buildbro.api.model.IBuild;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public interface BuildEndpoint {
    @PostMapping("/api/v1/builds/search")
    IBuildSearchResult search(@RequestBody IBuildSearch search);

    @GetMapping("/api/v1/builds/{buildId}")
    IBuild get(@PathVariable(name = "buildId") Long buildId) throws DataNotFoundException;


    @PostMapping("/api/v1/builds/create")
    IBuild create(@RequestBody IBuild build);

    @PostMapping("/api/v1/builds/add-labels/{buildId}")
    IBuild addLabels(@PathVariable(name = "buildId") Long buildId, @RequestBody Map<String, String> buildLabels) throws DataNotFoundException;
}
