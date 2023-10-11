package com.gaseng.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    SUPERVISOR("ROLE_SUPERVISOR", "슈퍼바이저"),
    ADMIN("ROLE_ADMIN", "관리자")
    ;

    private final String authority;
    private final String description;
}
