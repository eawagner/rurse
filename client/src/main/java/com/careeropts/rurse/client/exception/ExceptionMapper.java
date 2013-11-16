package com.careeropts.rurse.client.exception;


import static org.apache.http.HttpStatus.*;

public class ExceptionMapper {

    public static RurseAppException responseException(int code, String message) {
        switch (code) {
            case SC_BAD_REQUEST:
                return new BadRequestException(message);
            case SC_UNAUTHORIZED:
                return new UnauthorizedException(message);
            case SC_FORBIDDEN:
                return new ForbiddenException(message);
            case SC_NOT_FOUND:
                return new NotFoundException(message);
            case SC_INTERNAL_SERVER_ERROR:
                return new ServerErrorException(message);
            default:
                return new SimpleRestException(code, message);
        }


    }


}
