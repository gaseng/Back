package com.gaseng.checklist.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CheckCigarette {
    SMOKER("흡연자"),
    NON_SMOKER("비흡연자"),
    ;

    private String value;
}
