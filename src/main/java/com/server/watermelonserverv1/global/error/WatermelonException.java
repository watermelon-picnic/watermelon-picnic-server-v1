package com.server.watermelonserverv1.global.error;

import lombok.Getter;

@Getter
public class WatermelonException extends RuntimeException{

    private final ErrorCode errorCode;

    public WatermelonException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
