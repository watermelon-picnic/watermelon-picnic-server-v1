package com.server.watermelonserverv1.domain.region.exception;

import com.server.watermelonserverv1.global.error.ErrorCode;
import com.server.watermelonserverv1.global.error.WatermelonException;

public class RegionAlreadyExistException extends WatermelonException {
    public static final RegionAlreadyExistException EXCEPTION = new RegionAlreadyExistException();
    private RegionAlreadyExistException() { super(ErrorCode.REGION_ALREADY_EXIST_EXCEPTION); }
}
