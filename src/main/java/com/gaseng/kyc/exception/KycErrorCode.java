package com.gaseng.kyc.exception;

import com.gaseng.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum KycErrorCode implements ErrorCode {
	KYC_REQUIRE_NOT_FOUND(HttpStatus.NOT_FOUND, "KYC_001", "KYC 요청을 찾을 수 없습니다."),
	KYC_REQUIRE_INACTIVE(HttpStatus.NOT_FOUND, "KYC_002", "비활성화된 KYC 요청입니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
