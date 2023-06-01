package com.server.watermelonserverv1.domain.region.exception;

import com.server.watermelonserverv1.global.error.ErrorCode;
import com.server.watermelonserverv1.global.error.WatermelonException;

public class RegionNotFoundException extends WatermelonException {
    public static final RegionNotFoundException EXCEPTION = new RegionNotFoundException();
    private RegionNotFoundException() { super(ErrorCode.REGION_NOT_FOUND_EXCEPTION); }
}
