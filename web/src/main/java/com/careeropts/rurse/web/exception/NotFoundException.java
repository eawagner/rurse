package com.careeropts.rurse.web.exception;


import javax.ws.rs.WebApplicationException;

import static com.sun.jersey.api.Responses.NOT_FOUND;
import static com.sun.jersey.api.Responses.notFound;
import static javax.ws.rs.core.Response.status;

public class NotFoundException extends WebApplicationException{
    public NotFoundException() {
        super(notFound().build());
    }

    /**
     * Create a HTTP 404 (Not Found) exception.
     * @param message the String that is the entity of the 404 response.
     */
    public NotFoundException(String message) {
        super(status(NOT_FOUND).
                entity(message).type("text/plain").build());
    }
}
