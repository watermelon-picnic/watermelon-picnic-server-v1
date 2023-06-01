package com.server.watermelonserverv1.infrastructure.aws.exception;

import com.server.watermelonserverv1.global.error.ErrorCode;
import com.server.watermelonserverv1.global.error.WatermelonException;

public class ImageNotSaveException extends WatermelonException {
    public static final ImageNotSaveException EXCEPTION = new ImageNotSaveException();
    public ImageNotSaveException() { super(ErrorCode.IMAGE_NOT_FOUND_EXCEPTION); }
}
