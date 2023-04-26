package com.server.watermelonserverv1.domain.auth.exception;

import com.server.watermelonserverv1.global.error.ErrorCode;
import com.server.watermelonserverv1.global.error.WatermelonException;

public class ExistEmailException extends WatermelonException {

    public static final ExistEmailException EXCEPTION
            = new ExistEmailException();

    private ExistEmailException() {
        super(ErrorCode.EXIT_EMAIL_PRECONDITION_FAILED);
    }
}
