package com.careeropts.rurse.client.exception;

import java.net.URISyntaxException;

/**
 * A {@link RuntimeException} for handling URI related problems.
 */
public class UriException extends RurseAppException {
    public UriException(URISyntaxException cause) {
        super(cause);
    }
}
