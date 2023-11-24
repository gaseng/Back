package com.gaseng.jwt.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RedisTokenKey {
    TOKEN_KEY("Token-%d"),
    ;

    private final String value;
}