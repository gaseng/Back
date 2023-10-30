package com.gaseng.certification.exception;

import com.gaseng.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CertificationErrorCode implements ErrorCode {

    CERTIFICATION_MISMATCH(HttpStatus.UNAUTHORIZED, "CERTIFICATION_001", "인증번호가 일치하지 않습니다."),

    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
