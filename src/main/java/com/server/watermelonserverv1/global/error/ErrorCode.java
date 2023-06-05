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
    EMAIL_NOT_MATCHED_REQUIREMENT("email 이 요구사항과 맞지 않습니다.", 400),
    EMAIL_CONFLICT("email 전송 중 충돌이 발생했습니다.",409),
    TOKEN_TYPE_NOT_MATCHED("입력받은 토큰으로 인가되지 않았습니다.", 403),
    NICKNAME_BAD_REQUEST("유효한 닉네임으로 요청하시오.", 400),
    WRITER_NOT_FOUND("작성자로 등록되지 않았습니다.", 404),
    POST_NOT_FOUND("게시물을 찾을 수 없습니다.", 404),
    IMAGE_NOT_FOUND_EXCEPTION("사진이 저장되지 않았습니다.", 409),
    IMAGE_BAD_REQUEST_EXCEPTION("사진 요청형식이 알맞지 않습니다.", 415),
    REGION_NOT_FOUND_EXCEPTION("요청한 지역의 정보를 찾을 수 없습니다.", 404),
    WRITER_POST_INCORRECT_EXCEPTION("작성자만 접근할 수 있습니다.", 400),
    COMMENT_NOT_FOUND_EXCEPTION("댓글을 찾을 수 없습니다.", 404),
    REGION_ALREADY_EXIST_EXCEPTION("해당 지역은 이미 등록되어있습니다.", 409);

    private final String message;
    private final int statusCode;

    ErrorCode(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
