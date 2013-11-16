package com.careeropts.rurse.client.exception;

/**
 * A {@link RuntimeException} for generating all errors in the application
 */
public class RurseAppException extends RuntimeException {
    public RurseAppException() {
    }

    public RurseAppException(String message) {
        super(message);
    }

    public RurseAppException(String message, Throwable cause) {
        super(message, cause);
    }

    public RurseAppException(Throwable cause) {
        super(cause);
    }

    public RurseAppException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
