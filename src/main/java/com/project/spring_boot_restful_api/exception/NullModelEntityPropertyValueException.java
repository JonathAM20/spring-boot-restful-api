package com.project.spring_boot_restful_api.exception;

public class NullModelEntityPropertyValueException extends RuntimeException {

    public NullModelEntityPropertyValueException(final String message) {
        super(message);
    }

}
