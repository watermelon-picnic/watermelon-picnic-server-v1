package com.server.watermelonserverv1.global.error;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorResponse {

    private String message;

    private int status;

    @Builder
    public ErrorResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
