package com.project.spring_boot_restful_api.exception;

public class ModelEntityNotFoundException extends RuntimeException {

    public ModelEntityNotFoundException(final String message) {
        super(message);
    }

}
