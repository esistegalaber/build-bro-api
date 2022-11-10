package com.egalaber.buildbro.core.events;

import com.egalaber.buildbro.api.model.IBuild;

public class BuildCreatedEvent {
    private final IBuild build;

    public BuildCreatedEvent(IBuild build) {
        this.build = build;
    }

    public IBuild getBuild() {
        return build;
    }

    @Override
    public String toString() {
        return "BuildCreatedEvent{" +
                "build=" + build +
                '}';
    }
}
