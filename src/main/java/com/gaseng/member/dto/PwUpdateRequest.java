package com.gaseng.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record PwUpdateRequest (
    Long id,
    @Size(min = 8, max = 12, message = "8자 이상, 12자 이하여야 합니다.")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Schema(description = "비밀번호", example = "pass1234!")
    String password

) {

}

