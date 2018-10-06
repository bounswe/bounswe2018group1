package com.cmpe451.retro.core;

import com.cmpe451.retro.models.ErrorResponse;
import com.cmpe451.retro.models.RetroException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity indibuExceptionHandler(RetroException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage()), exception.getHttpStatus());
    }

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity defaultExceptionHandler(Exception exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity uniqueConstraintExceptionHandler(DataIntegrityViolationException exception) {

        if (exception.getMessage().contains("PUBLIC.USER(EMAIL)")) {
            return new ResponseEntity<>(new ErrorResponse("There already is an account with this email."), HttpStatus.BAD_REQUEST);
        }

        if (exception.getMessage().contains("PUBLIC.USER(NICK_NAME)")) {
            return new ResponseEntity<>(new ErrorResponse("This nickname is already in use."), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new ErrorResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity notNullConstraintExceptionHandler(ConstraintViolationException exception) {

        if (exception.getMessage().contains("javax.validation.constraints.NotNull")) {
            return new ResponseEntity<>(new ErrorResponse("Please fill all the necessary fields."), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(new ErrorResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

}