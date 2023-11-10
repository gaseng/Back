package com.gaseng.sharehouse.exception;

import com.gaseng.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SharehouseErrorCode implements ErrorCode {
	ACCESS_DENIED_S3(HttpStatus.INTERNAL_SERVER_ERROR, "SHAREHOUSE_001", "AWS S3 서비스에서 내부 오류가 감지되었습니다."),
	USER_ID_MISMATCH(HttpStatus.FORBIDDEN, "SHAREHOUSE_002", "잘못된 사용자가 요청했습니다."),
	SHAREHOUSE_NOT_FOUND(HttpStatus.NOT_FOUND, "SHAREHOUSE_003", "쉐어하우스 글을 찾을 수 없습니다."),
	DUPLICATE_SCRAP(HttpStatus.CONFLICT, "SCRAP_001", "이미 스크랩한 게시물입니다."),
	;
	
	private final HttpStatus status;
	private final String code;
	private final String message;
}
