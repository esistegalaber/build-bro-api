package com.egalaber.buildbro.api.fault;

import com.egalaber.buildbro.api.model.IExceptionInfo;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class BuildBroExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BuildBroException.class)
    ResponseEntity<IExceptionInfo> handleConversion(BuildBroException ex) {
        IExceptionInfo body = new IExceptionInfo(
                ex.getKey(),
                ex.getMessage(),
                ex.getDescription()
        );
        return new ResponseEntity<>(body, new HttpHeaders(), ex.getStatus());
    }
}
