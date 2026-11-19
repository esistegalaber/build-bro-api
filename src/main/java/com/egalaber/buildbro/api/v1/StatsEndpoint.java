package com.egalaber.buildbro.api.v1;

import com.egalaber.buildbro.api.model.IBuildBroStats;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "Stats", description = "Overall numbers, e.g. for a dashboard.")
public interface StatsEndpoint {

    @Operation(
            operationId = "getStats",
            summary = "Counts of projects, branches, builds and labels")
    @GetMapping("/api/v1/stats")
    IBuildBroStats stats();
}
