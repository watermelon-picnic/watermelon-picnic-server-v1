package com.server.watermelonserverv1.global.exception;

import com.server.watermelonserverv1.global.error.ErrorCode;
import com.server.watermelonserverv1.global.error.WatermelonException;

public class UserNotFoundException extends WatermelonException {
    public final static WatermelonException EXCEPTION
            = new UserNotFoundException();
    private UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
