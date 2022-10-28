package com.egalaber.buildbro.core.events;

import org.springframework.context.ApplicationEvent;

public class BuildCreatedEvent {
    private final String projectName;
    private final String branchName;

    public BuildCreatedEvent(String projectName, String branchName) {
        this.projectName = projectName;
        this.branchName = branchName;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getBranchName() {
        return branchName;
    }

    @Override
    public String toString() {
        return "BuildCreatedEvent{" +
                "projectName='" + projectName + '\'' +
                ", branchName='" + branchName + '\'' +
                '}';
    }
}
