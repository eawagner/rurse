package com.careeropts.rurse.client.exception;


import static org.apache.http.HttpStatus.SC_BAD_REQUEST;

public class BadRequestException extends SimpleRestException {
    public BadRequestException(String message) {
        super(SC_BAD_REQUEST, message);
    }
}
