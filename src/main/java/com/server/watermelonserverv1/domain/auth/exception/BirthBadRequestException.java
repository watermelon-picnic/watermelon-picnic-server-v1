package com.server.watermelonserverv1.domain.auth.exception;

import com.server.watermelonserverv1.global.error.ErrorCode;
import com.server.watermelonserverv1.global.error.WatermelonException;

public class BirthBadRequestException extends WatermelonException {
    public static final BirthBadRequestException EXCEPTION =
            new BirthBadRequestException();
    private BirthBadRequestException() {
        super(ErrorCode.BIRTH_BAD_REQUEST);
    }
}
