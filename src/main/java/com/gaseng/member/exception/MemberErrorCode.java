package com.gaseng.member.exception;

import com.gaseng.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorCode {
    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "MEMBER_001", "이메일 형식이 올바르지 않습니다."),
    INVALID_PASSWORD_FORMAT(HttpStatus.BAD_REQUEST, "MEMBER_002", "비밀번호 형식이 올바르지 않습니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "MEMBER_003", "이미 등록된 이메일입니다."),
    DUPLICATE_PHONE(HttpStatus.CONFLICT, "MEMBER_004", "이미 등록된 번호입니다."),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "MEMBER_005", "이미 등록된 닉네임입니다."),
    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
