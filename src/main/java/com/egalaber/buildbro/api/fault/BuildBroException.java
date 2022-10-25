package com.egalaber.buildbro.api.fault;

import org.springframework.http.HttpStatus;

public abstract class BuildBroException extends Exception {
    private final HttpStatus status;
    private final String key;
    private final String description;

    BuildBroException(String message, HttpStatus status, String key, String description) {
        super(message);
        this.status = status;
        this.key = key;
        this.description = description;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }
}
