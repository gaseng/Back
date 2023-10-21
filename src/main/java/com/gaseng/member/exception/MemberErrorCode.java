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
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER_006", "회원을 찾을 수 없습니다."),
    PASSWORD_MISMATCH(HttpStatus.UNAUTHORIZED, "MEMBER_007", "비밀번호가 일치하지 않습니다."),
    IS_SAME_STATUS(HttpStatus.CONFLICT, "MEMBER_008", "요청과 동일한 상태입니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
