package com.server.watermelonserverv1.global.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_NOT_FOUND("사용자 정보를 찾을 수 없습니다.", 404),
    PASSWORD_INCORRECT("비밀번호가 일치하지 않습니다.", 400),
    TOKEN_NOT_FOUND("해당 아이디로 저장된 token 정보가 존재하지 않습니다.", 404),
    TOKEN_EXPIRED("토큰이 만료되었습니다.", 403),
    AUTH_BAD_REQUEST("인증을 위해 토큰을 동봉해 주십시오.", 400),
    EXIT_EMAIL_PRECONDITION_FAILED("이미 존재하는 이메일입니다", 400),
    EXIT_NICKNAME_PRECONDITION_FAILED("이미 존재하는 닉네임입니다.", 400),
    BIRTH_BAD_REQUEST("잘못된 생년월일입니다.", 400),
    EMAIL_NOT_MATCHED_REQUIREMENT("email 이 요구사항과 맞지 않습니다.", 400);

    private final String message;
    private final int statusCode;

    ErrorCode(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
