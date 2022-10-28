package com.egalaber.buildbro.core.events;

import com.egalaber.buildbro.core.service.TrackingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BuildCreatedEventListener {
    private final TrackingService trackingService;

    public BuildCreatedEventListener(TrackingService trackingService) {
        this.trackingService = trackingService;
    }

    @Async
    @EventListener
    public void handleBuildCreated(BuildCreatedEvent buildCreatedEvent) {
        log.info("onBuildCreated(buildCreatedEvent={})", buildCreatedEvent);
        trackingService.handleBuildCreated(buildCreatedEvent.getProjectName(), buildCreatedEvent.getBranchName());
    }
}
