package com.orderme.ordermebackend.exception;

public class ExceptionEntity {

    private String message;

    public ExceptionEntity(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
