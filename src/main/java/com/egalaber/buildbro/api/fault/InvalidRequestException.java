package com.egalaber.buildbro.api.fault;

import org.springframework.http.HttpStatus;

public class InvalidRequestException extends BuildBroException {
    public InvalidRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "invalid-request", "Your request was not valid.");
    }
}
