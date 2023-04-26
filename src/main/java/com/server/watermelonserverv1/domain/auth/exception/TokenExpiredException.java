package com.server.watermelonserverv1.domain.auth.exception;

import com.server.watermelonserverv1.global.error.ErrorCode;
import com.server.watermelonserverv1.global.error.WatermelonException;

public class TokenExpiredException extends WatermelonException {
    public static final TokenExpiredException EXCEPTION
            = new TokenExpiredException();
    private TokenExpiredException() {
        super(ErrorCode.TOKEN_EXPIRED);
    }
}
