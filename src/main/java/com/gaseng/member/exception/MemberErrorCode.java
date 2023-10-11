package com.gaseng.member.exception;

import com.gaseng.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorCode {
    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "USER_001", "이메일 형식이 올바르지 않습니다."),
    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
