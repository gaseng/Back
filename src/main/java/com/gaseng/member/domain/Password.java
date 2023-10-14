package com.gaseng.member.domain;

import com.gaseng.global.exception.BaseException;
import com.gaseng.member.exception.MemberErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.regex.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Password {
    private static final String PASSWORD_PATTERN = "^(?=.*[a-zA-Z])(?=.*[!@#$%^&*+=-])(?=.*[0-9]).{8,12}$";
    private static final Pattern PASSWORD_MATCHER = Pattern.compile(PASSWORD_PATTERN);
    public static final PasswordEncoder ENCODER = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    private static final int REPEATING_LIMIT = 3;
    private static final int CONSECUTIVE_LIMIT = 3;

    @Column(name = "mem_password", nullable = false)
    private String value;

    private Password(String value) {
        this.value = value;
    }

    public static Password encrypt(String value, PasswordEncoder encoder) {
        validatePassword(value);
        return new Password(encoder.encode(value));
    }

    private static void validatePassword(String value) {
        if (isNotValidPattern(value)) {
            throw BaseException.type(MemberErrorCode.INVALID_PASSWORD_FORMAT);
        }    }

    private static boolean isNotValidPattern(String password) {
        return !PASSWORD_MATCHER.matcher(password).matches();
    }

    public boolean isSamePassword(String comparePassword, PasswordEncoder encoder) {
        return encoder.matches(comparePassword, this.value);
    }
}