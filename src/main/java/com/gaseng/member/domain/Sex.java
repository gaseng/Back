package com.gaseng.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Sex {
    MALE("남자"),
    FEMALE("여자")
    ;

    private final String value;
}
