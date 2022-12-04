package com.egalaber.buildbro.api.v1;

import com.egalaber.buildbro.api.model.IBuildBroStats;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "stats", description = "Provides Statistics.")
public interface StatsEndpoint {
    @GetMapping("/api/v1/stats")
    IBuildBroStats stats();
}
