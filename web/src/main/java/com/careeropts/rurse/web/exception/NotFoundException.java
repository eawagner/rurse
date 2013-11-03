package com.careeropts.rurse.web.exception;


import static com.sun.jersey.api.Responses.notFound;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN_TYPE;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.status;

public class NotFoundException extends WebAppResponseException {
    public NotFoundException() {
        super(notFound().entity("").type(TEXT_PLAIN_TYPE).build());
    }

    /**
     * Create a HTTP 404 (Not Found) exception.
     * @param message the String that is the entity of the 404 response.
     */
    public NotFoundException(String message) {
        super(status(NOT_FOUND).
                entity(message).type(TEXT_PLAIN_TYPE).build());
    }
}
