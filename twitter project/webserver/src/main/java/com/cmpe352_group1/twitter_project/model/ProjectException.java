package com.cmpe352_group1.twitter_project.model;


import org.springframework.http.HttpStatus;

public class ProjectException extends RuntimeException{

    private HttpStatus httpStatus;

    public ProjectException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public ProjectException() {
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
