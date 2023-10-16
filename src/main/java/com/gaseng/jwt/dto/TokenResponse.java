package com.gaseng.jwt.dto;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}
