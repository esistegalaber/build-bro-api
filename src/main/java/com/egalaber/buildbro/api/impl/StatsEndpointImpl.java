package com.egalaber.buildbro.api.impl;

import com.egalaber.buildbro.api.StatsEndpoint;
import com.egalaber.buildbro.api.model.IBuildBroStats;
import com.egalaber.buildbro.core.service.StatsService;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

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
