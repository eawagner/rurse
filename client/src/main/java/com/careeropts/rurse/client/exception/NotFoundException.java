package com.careeropts.rurse.client.exception;


import static org.apache.http.HttpStatus.SC_NOT_FOUND;

public class NotFoundException extends SimpleRestException {
    public NotFoundException(String message) {
        super(SC_NOT_FOUND, message);
    }
}
