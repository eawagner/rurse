package com.careeropts.rurse.client.exception;

import java.net.URISyntaxException;

/**
 * A {@link RuntimeException} for handling URI related problems.
 */
public class UriException extends RuntimeException {
    public UriException(URISyntaxException cause) {
        super(cause);
    }
}
