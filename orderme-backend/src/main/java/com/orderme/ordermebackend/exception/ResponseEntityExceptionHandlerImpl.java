package com.orderme.ordermebackend.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseEntityExceptionHandlerImpl extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex,
                                                                    WebRequest request) {
        ex.printStackTrace();
        return handleExceptionInternal(ex, new ExceptionEntity(ex.getMessage()), new HttpHeaders(),
                HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex, WebRequest request) {
        ex.printStackTrace();
        return handleExceptionInternal(ex, new ExceptionEntity(ex.getMessage()),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}
