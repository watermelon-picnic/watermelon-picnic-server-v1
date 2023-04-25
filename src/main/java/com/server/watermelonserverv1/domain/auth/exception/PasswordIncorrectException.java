package com.server.watermelonserverv1.domain.auth.exception;

import com.server.watermelonserverv1.global.error.ErrorCode;
import com.server.watermelonserverv1.global.error.WatermelonException;

public class PasswordIncorrectException extends WatermelonException {
    public static final PasswordIncorrectException EXCEPTION
            = new PasswordIncorrectException();
    private PasswordIncorrectException() {
        super(ErrorCode.PASSWORD_INCORRECT);
    }
}
