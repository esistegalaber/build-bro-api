package com.egalaber.buildbro.api.fault;


import org.springframework.http.HttpStatus;

public class DataNotFoundException extends BuildBroException {
    public DataNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND, "not-found", "No such data");
    }
}
