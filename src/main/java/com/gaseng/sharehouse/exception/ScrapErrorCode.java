package com.gaseng.sharehouse.exception;

import com.gaseng.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ScrapErrorCode implements ErrorCode {
    SCRAP_NOT_FOUND(HttpStatus.NOT_FOUND, "SCRAP_001", "스크랩 정보를 찾을 수 없습니다."),
    DUPLICATE_SCRAP(HttpStatus.CONFLICT, "SCRAP_002", "이미 스크랩한 게시물입니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
