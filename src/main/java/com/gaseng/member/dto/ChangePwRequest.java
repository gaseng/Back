package com.gaseng.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record ChangePwRequest (
        @Size(min = 5, max = 50)
        @NotBlank(message = "이메일은 필수입니다.")
        @Schema(description = "이메일", example = "example@gmail.com")
        String email,
        @Size(min = 2, max = 10, message = "2글자 이상이어야 합니다.")
        @NotBlank(message = "이름은 필수입니다.")
        @Schema(description = "이름", example = "홍길동")
        String name,

        @Size(min = 10, max = 11, message = "숫자만 입력해주세요.")
        @NotBlank(message = "전화번호는 필수입니다.")
        @Schema(description = "전화번호", example = "01012345678")
        String phone
) {

}

