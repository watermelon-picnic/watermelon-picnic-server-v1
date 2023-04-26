package com.server.watermelonserverv1.global.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_NOT_FOUND("사용자 정보를 찾을 수 없습니다.", 404),
    PASSWORD_INCORRECT("비밀번호가 일치하지 않습니다.", 400),
    TOKEN_NOT_FOUND("해당 아이디로 저장된 token 정보가 존재하지 않습니다.", 404),
    TOKEN_EXPIRED("토큰이 만료되었습니다.", 403),
    EXIT_EMAIL_PRECONDITION_FAILED("이미 존재하는 이메일 입니다", 412);

    private final String message;
    private final int statusCode;

    ErrorCode(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
