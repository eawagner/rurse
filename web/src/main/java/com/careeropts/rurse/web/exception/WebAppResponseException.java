package com.careeropts.rurse.web.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;


/**
 * Abstract exception to allow for simple generation of error responses.
 */
public abstract class WebAppResponseException extends WebApplicationException {
    public WebAppResponseException(Response response) {
        super(response);
    }
}
