package com.gaseng.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginResponse(
        @Schema(description = "member id (index)", example = "1")
        Long memId,
        @Schema(description = "member email", example = "example@gmail.com")
        String email,
        @Schema(description = "access token", example = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6NSwiaWF0Ijox123456wMDM5LCJleHAiOjE3MDAyNzIwMzl9.0mjwFO6JtabcdefgrPgGm-2XWY60G2YH6HbTwU386Po")
        String accessToken,
        @Schema(description = "refresh token", example = "eyABCDciOiJIUzI1NiJ9.abcdefg6NSwiaWF0IjoxNjk3NjgwMDM5LCJleHAiOjE3Mxyz987wMzl9.0mjwFO6Jtqz616yprPgGm-2XWY60G2YH6HbTwU123q0")
        String refreshToken
) {
}