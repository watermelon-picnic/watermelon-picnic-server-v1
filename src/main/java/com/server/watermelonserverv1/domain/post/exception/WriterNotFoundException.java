package com.server.watermelonserverv1.domain.post.exception;

import com.server.watermelonserverv1.global.error.ErrorCode;
import com.server.watermelonserverv1.global.error.WatermelonException;

public class WriterNotFoundException extends WatermelonException {
    public static final WriterNotFoundException EXCEPTION =
            new WriterNotFoundException();
    private WriterNotFoundException() {
        super(ErrorCode.WRITER_NOT_FOUND);
    }
}
