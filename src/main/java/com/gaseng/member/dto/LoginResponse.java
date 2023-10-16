package com.gaseng.member.dto;

public record LoginResponse(
        Long memId,
        String email,
        String accessToken,
        String refreshToken
) {
}