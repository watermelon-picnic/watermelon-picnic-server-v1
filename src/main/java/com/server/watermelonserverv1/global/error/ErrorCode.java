package com.server.watermelonserverv1.global.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_NOT_FOUND("사용자 정보를 찾을 수 없습니다.", 404),
    EXIT_EMAIL_PRECONDITION_FAILED("이미 존재하는 이메일 입니다", 412);

    private final String message;
    private final int statusCode;

    ErrorCode(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
