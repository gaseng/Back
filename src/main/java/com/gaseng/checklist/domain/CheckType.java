package com.gaseng.checklist.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CheckType {
    ACTIVE("활동적"),
    PASSIVE("소극적"),
    ;

    private String value;
}
