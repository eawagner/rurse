package com.careeropts.rurse.client.exception;

public class SimpleRestException extends RurseAppException{

    private final int responseCode;

    public SimpleRestException(int responseCode, String message) {
        super(String.format("(%d) %s", responseCode, message));
        this.responseCode = responseCode;
    }

    public int getResponseCode() {
        return responseCode;
    }
}
