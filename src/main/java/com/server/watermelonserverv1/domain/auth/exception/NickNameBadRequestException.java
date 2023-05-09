package com.server.watermelonserverv1.domain.auth.exception;

import com.server.watermelonserverv1.global.error.ErrorCode;
import com.server.watermelonserverv1.global.error.WatermelonException;

public class NickNameBadRequestException extends WatermelonException {
    public static final NickNameBadRequestException EXCEPTION =
            new NickNameBadRequestException();
    private NickNameBadRequestException() {
        super(ErrorCode.NICKNAME_BAD_REQUEST);
    }
}
