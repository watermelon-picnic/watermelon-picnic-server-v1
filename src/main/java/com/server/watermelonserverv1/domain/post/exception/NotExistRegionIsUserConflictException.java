package com.server.watermelonserverv1.domain.post.exception;

import com.server.watermelonserverv1.global.error.ErrorCode;
import com.server.watermelonserverv1.global.error.WatermelonException;

public class NotExistRegionIsUserConflictException extends WatermelonException {
    public static final NotExistRegionIsUserConflictException EXCEPTION = new NotExistRegionIsUserConflictException();
    private NotExistRegionIsUserConflictException() { super(ErrorCode.NOT_EXIST_REGION_IN_USER); }
}
