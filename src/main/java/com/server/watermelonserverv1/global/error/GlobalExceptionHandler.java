package com.server.watermelonserverv1.global.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({WatermelonException.class})
    protected ResponseEntity<ErrorResponse> handlerWatermelonException(WatermelonException e) {
        ErrorCode ex = e.getErrorCode();
        return new ResponseEntity<>(ErrorResponse.builder()
                .message(ex.getMessage())
                .status(ex.getStatusCode())
                .build(),
                HttpStatus.valueOf(ex.getStatusCode()));
    }
}
