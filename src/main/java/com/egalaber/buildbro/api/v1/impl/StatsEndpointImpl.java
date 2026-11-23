package com.egalaber.buildbro.api.v1.impl;

import com.egalaber.buildbro.api.model.IBuildBroStats;
import com.egalaber.buildbro.api.v1.StatsApi;
import com.egalaber.buildbro.core.service.StatsService;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implements {@link StatsApi}, which is generated from the contract ({@code openapi/build-bro-api.yaml}).
 */
@RestController
@Transactional
public class StatsEndpointImpl implements StatsApi {
    private final StatsService statsService;

    public StatsEndpointImpl(StatsService statsService) {
        this.statsService = statsService;
    }

    @Override
    public IBuildBroStats getStats() {
        return statsService.stats();
    }
}
