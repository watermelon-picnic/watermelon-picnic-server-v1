package com.server.watermelonserverv1.global.exception;

import com.server.watermelonserverv1.global.error.ErrorCode;
import com.server.watermelonserverv1.global.error.WatermelonException;

public class AuthBadRequestException extends WatermelonException {
    public static final AuthBadRequestException EXCEPTION =
            new AuthBadRequestException();
    private AuthBadRequestException() {
        super(ErrorCode.AUTH_BAD_REQUEST);
    }
}
