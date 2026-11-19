package com.egalaber.buildbro.api.model;

import java.io.Serializable;

public record IBuildNumber(String project, String branch, Long number) implements Serializable {

    public IBuildNumber {
        number = number == null ? 0L : number;
    }

    /** A build number that has not been counted yet (number 0). */
    public IBuildNumber(String project, String branch) {
        this(project, branch, 0L);
    }
}
