package com.orderme.ordermebackend.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ResponseEntityExceptionHandlerImpl extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex,
                                                                    WebRequest request) {
        ex.printStackTrace();
        return handleExceptionInternal(ex, new ExceptionEntity(ex.getMessage()), new HttpHeaders(),
                HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex,
                                                                   WebRequest request) {
        ex.printStackTrace();
        return handleExceptionInternal(ex, new ExceptionEntity(ex.getMessage()), new HttpHeaders(),
                HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    protected ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException ex,
                                                               WebRequest request) {
        ex.printStackTrace();
        return handleExceptionInternal(ex, new ExceptionEntity(ex.getMessage()), new HttpHeaders(),
                HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex, WebRequest request) {
        ex.printStackTrace();
        return handleExceptionInternal(ex, new ExceptionEntity(ex.getMessage()),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }



}
