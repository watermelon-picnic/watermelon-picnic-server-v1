package com.server.watermelonserverv1.domain.auth.exception;

import com.server.watermelonserverv1.global.error.ErrorCode;
import com.server.watermelonserverv1.global.error.WatermelonException;

public class EmailBadRequestException extends WatermelonException {
    public static final EmailBadRequestException EXCEPTION =
            new EmailBadRequestException();
    private EmailBadRequestException() {
        super(ErrorCode.EMAIL_NOT_MATCHED_REQUIREMENT);
    }
}
