package com.server.watermelonserverv1.infrastructure.aws.exception;

import com.server.watermelonserverv1.global.error.ErrorCode;
import com.server.watermelonserverv1.global.error.WatermelonException;

public class ImageBadRequestException extends WatermelonException {
    public static final ImageBadRequestException EXCEPTION = new ImageBadRequestException();
    private ImageBadRequestException() { super(ErrorCode.IMAGE_BAD_REQUEST_EXCEPTION); }
}
