package com.egalaber.buildbro.api.fault;

import org.springframework.http.HttpStatus;

/**
 * An Exception thrown when a provided Date/Datetime
 * is in the future and a future Date/Datetime is not
 * supported by the method call.
 */
public class FutureDateException extends BuildBroException {
    public FutureDateException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "future-request", "The provided at Date is in the future.");
    }
}
