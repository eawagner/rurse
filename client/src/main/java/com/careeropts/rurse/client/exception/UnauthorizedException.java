package com.careeropts.rurse.client.exception;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

public class UnauthorizedException extends SimpleRestException {
    public UnauthorizedException(String message) {
        super(SC_UNAUTHORIZED, message);
    }
}
