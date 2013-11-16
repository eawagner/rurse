package com.careeropts.rurse.client.exception;


import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;

public class ServerErrorException extends SimpleRestException {
    public ServerErrorException(String message) {
        super(SC_INTERNAL_SERVER_ERROR, message);
    }
}
