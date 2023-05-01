package com.server.watermelonserverv1.domain.auth.exception;

import com.server.watermelonserverv1.global.error.ErrorCode;
import com.server.watermelonserverv1.global.error.WatermelonException;

public class MessageConfigException extends WatermelonException {
    public static final MessageConfigException EXCEPTION = new MessageConfigException();
    private MessageConfigException() { super(ErrorCode.EMAIL_CONFLICT); }
}
