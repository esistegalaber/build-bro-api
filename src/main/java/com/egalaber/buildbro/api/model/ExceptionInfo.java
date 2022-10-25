package com.egalaber.buildbro.api.model;

import java.io.Serializable;

public class ExceptionInfo implements Serializable {
    private String key;
    private String description;
    private String message;

    public String getKey() {
        return key;
    }

    public ExceptionInfo() {
    }

    public ExceptionInfo(String key, String description, String message) {
        this.key = key;
        this.description = description;
        this.message = message;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
