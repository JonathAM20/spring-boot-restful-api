package com.project.spring_boot_restful_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ModelEntityNotFoundException.class)
    public ResponseEntity<?> handleModelEntityNotFoundException(ModelEntityNotFoundException exception) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
