package com.careeropts.rurse.web.exception;


import javax.ws.rs.WebApplicationException;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN_TYPE;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.status;

public class InternalServerError extends WebApplicationException{
    public InternalServerError() {
        super(status(INTERNAL_SERVER_ERROR).entity("").type(TEXT_PLAIN_TYPE).build());
    }

    /**
     * Create a HTTP 404 (Not Found) exception.
     * @param message the String that is the entity of the 404 response.
     */
    public InternalServerError(String message) {
        super(status(INTERNAL_SERVER_ERROR).
                entity(message).type(TEXT_PLAIN_TYPE).build());
    }
}
