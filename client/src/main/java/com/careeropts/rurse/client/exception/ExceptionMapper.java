package com.careeropts.rurse.client.exception;


public class ExceptionMapper {

    public static void responseException(int code, String message) {
        //TODO map code to exception type.
        throw new RuntimeException(message);
    }


}
