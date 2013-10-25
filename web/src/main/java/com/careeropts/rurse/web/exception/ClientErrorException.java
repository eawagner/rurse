package com.careeropts.rurse.web.exception;


import javax.ws.rs.WebApplicationException;

import static com.sun.jersey.api.Responses.CLIENT_ERROR;
import static com.sun.jersey.api.Responses.clientError;
import static javax.ws.rs.core.Response.status;

public class ClientErrorException extends WebApplicationException{
    public ClientErrorException() {
        super(clientError().build());
    }

    /**
     * Create a HTTP 400 (Client Error) exception.
     * @param message the String that is the entity of the 400 response.
     */
    public ClientErrorException(String message) {
        super(status(CLIENT_ERROR).
                entity(message).type("text/plain").build());
    }
}
