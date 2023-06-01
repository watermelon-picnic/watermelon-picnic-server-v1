package com.server.watermelonserverv1.domain.post.exception;

import com.server.watermelonserverv1.global.error.ErrorCode;
import com.server.watermelonserverv1.global.error.WatermelonException;

public class WriterPostIncorrectException extends WatermelonException {
    public static final WriterPostIncorrectException EXCEPTION = new WriterPostIncorrectException();
    private WriterPostIncorrectException() { super(ErrorCode.WRITER_POST_INCORRECT_EXCEPTION); }
}
