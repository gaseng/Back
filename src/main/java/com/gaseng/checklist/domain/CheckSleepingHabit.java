package com.gaseng.checklist.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CheckSleepingHabit {
    NONE("코골이 없음"),
    MILD("코골이 약간"),
    LOUD("코골이 심함")
    ;

    private String value;
}
