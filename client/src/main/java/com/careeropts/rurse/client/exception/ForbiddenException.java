package com.careeropts.rurse.client.exception;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;

public class ForbiddenException extends SimpleRestException {
    public ForbiddenException(String message) {
        super(SC_FORBIDDEN, message);
    }
}
