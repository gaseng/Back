package com.gaseng.member.dto;

import com.gaseng.global.annotation.ValidEnum;
import com.gaseng.member.domain.Email;
import com.gaseng.member.domain.Member;
import com.gaseng.member.domain.Password;
import com.gaseng.member.domain.Sex;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static com.gaseng.member.domain.Password.ENCODER;

public record SignUpRequest(
        @Size(min = 5)
        @NotBlank(message = "이메일은 필수입니다.")
        @Schema(description = "이메일", example = "example@gmail.com")
        String email,

        @Size(min = 8, max = 12, message = "8자 이상, 12자 이하여야 합니다.")
        @NotBlank(message = "비밀번호는 필수입니다.")
        @Schema(description = "비밀번호", example = "pass1234!")
        String password,

        @Size(min = 2, message = "2글자 이상이어야 합니다.")
        @NotBlank(message = "이름은 필수입니다.")
        @Schema(description = "이름", example = "홍길동")
        String name,

        @Size(min = 2, message = "2글자 이상이어야 합니다.")
        @NotBlank(message = "닉네임은 필수입니다.")
        @Schema(description = "닉네임", example = "길동이")
        String nickname,

        @Size(message = "size에 메세지만 있어도 될까요?")
        @ValidEnum(enumClass = Sex.class)
        @Schema(description = "성별", example = "0")
        Sex sex,

        @Size(min = 10, max = 11, message = "숫자만 입력해주세요.")
        @NotBlank(message = "전화번호는 필수입니다.")
        @Schema(description = "전화번호", example = "01012345678")
        String phone
) {
    public Member toMember() {
        return Member.registerMember(
                Email.from(email),
                Password.encrypt(password, ENCODER),
                name,
                nickname,
                sex,
                phone
        );
    }
}
