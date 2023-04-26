package com.server.watermelonserverv1.global.exception;

import com.server.watermelonserverv1.global.error.ErrorCode;
import com.server.watermelonserverv1.global.error.WatermelonException;

public class TokenNotFoundException extends WatermelonException {
    public static final WatermelonException EXCEPTION
            = new TokenNotFoundException();
    private TokenNotFoundException() {
        super(ErrorCode.TOKEN_NOT_FOUND);
    }
}
