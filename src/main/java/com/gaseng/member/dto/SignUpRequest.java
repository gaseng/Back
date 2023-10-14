package com.gaseng.member.dto;

import com.gaseng.global.annotation.ValidEnum;
import com.gaseng.member.domain.Email;
import com.gaseng.member.domain.Member;
import com.gaseng.member.domain.Password;
import com.gaseng.member.domain.Sex;

import javax.validation.constraints.NotBlank;

import static com.gaseng.member.domain.Password.ENCODER;

public record SignUpRequest(
        @NotBlank(message = "이메일은 필수입니다.")
        String email,

        @NotBlank(message = "비밀번호는 필수입니다.")
        String password,

        @NotBlank(message = "이름은 필수입니다.")
        String name,

        @NotBlank(message = "닉네임은 필수입니다.")
        String nickname,

        @ValidEnum(enumClass = Sex.class)
        Sex sex,

        @NotBlank(message = "전화번호는 필수입니다.")
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
