package com.careeropts.rurse.web.exception;


import javax.ws.rs.WebApplicationException;

import static com.sun.jersey.api.Responses.clientError;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN_TYPE;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.status;

public class BadRequestException extends WebApplicationException{
    public BadRequestException() {
        super(clientError().build());
    }

    /**
     * Create a HTTP 400 (Bad Request) exception.
     * @param message the String that is the entity of the 400 response.
     */
    public BadRequestException(String message) {
        super(status(BAD_REQUEST).
                entity(message).type(TEXT_PLAIN_TYPE).build());
    }
}
