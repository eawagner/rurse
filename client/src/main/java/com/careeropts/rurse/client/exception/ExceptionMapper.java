package com.careeropts.rurse.client.exception;


public class ExceptionMapper {

    public static RuntimeException responseException(int code, String message) {
        //TODO map code to exception type.
        return new RuntimeException(message);
    }


}
