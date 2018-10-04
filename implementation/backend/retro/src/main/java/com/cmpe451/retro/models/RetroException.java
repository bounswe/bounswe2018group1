package com.cmpe451.retro.models;

import org.springframework.http.HttpStatus;

public class RetroException extends RuntimeException {

    private HttpStatus httpStatus;

    public RetroException() {
    }

    public RetroException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }


    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}