package com.egalaber.buildbro.api.v1.impl;

import com.egalaber.buildbro.api.model.IBuildBroStats;
import com.egalaber.buildbro.api.v1.StatsEndpoint;
import com.egalaber.buildbro.core.service.StatsService;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class StatsEndpointImpl implements StatsEndpoint {
    private final StatsService statsService;

    public StatsEndpointImpl(StatsService statsService) {
        this.statsService = statsService;
    }

    @Override
    public IBuildBroStats stats() {
        return statsService.stats();
    }
}
