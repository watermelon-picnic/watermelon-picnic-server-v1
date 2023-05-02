package com.server.watermelonserverv1.domain.auth.exception;

import com.server.watermelonserverv1.global.error.ErrorCode;
import com.server.watermelonserverv1.global.error.WatermelonException;

public class TokenTypeNotMatchedException extends WatermelonException {
    public static final TokenTypeNotMatchedException EXCEPTION
            = new TokenTypeNotMatchedException();
    private TokenTypeNotMatchedException() {
        super(ErrorCode.TOKEN_TYPE_NOT_MATCHED);
    }
}
