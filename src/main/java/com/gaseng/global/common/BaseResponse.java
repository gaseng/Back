package com.gaseng.global.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.gaseng.global.exception.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.gaseng.global.exception.GlobalErrorCode.SUCCESS;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@JsonPropertyOrder({"status", "code", "message", "data"})
public class BaseResponse<T> {
    @Schema(description = "HTTP Status Code", example = "200")
    private int status;

    @Schema(description = "HTTP Status Code Message", example = "SUCCESS")
    private String code;

    @Schema(description = "Response Message", example = "요청에 성공했습니다.")
    private String message;

    @Schema(description = "Response Data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public BaseResponse(T result) {
        this.status = SUCCESS.getStatus().value();
        this.code = SUCCESS.getCode();
        this.message = SUCCESS.getMessage();
        this.data = result;
    }

    private BaseResponse(ErrorCode code) {
        this.status = code.getStatus().value();
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    public BaseResponse(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public static BaseResponse from(ErrorCode code) {
        return new BaseResponse(code);
    }

    public static BaseResponse of(ErrorCode code, String message) {
        return new BaseResponse(code.getStatus().value(), code.getCode(), message);
    }
}
