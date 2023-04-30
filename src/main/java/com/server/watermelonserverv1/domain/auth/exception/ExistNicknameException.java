package com.server.watermelonserverv1.domain.auth.exception;

import com.server.watermelonserverv1.global.error.ErrorCode;
import com.server.watermelonserverv1.global.error.WatermelonException;

public class ExistNicknameException extends WatermelonException {
    public static final ExistNicknameException EXCEPTION =
            new ExistNicknameException();
    private ExistNicknameException() {
        super(ErrorCode.EXIT_NICKNAME_PRECONDITION_FAILED);
    }
}
