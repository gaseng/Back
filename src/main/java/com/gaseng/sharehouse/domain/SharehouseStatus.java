package com.gaseng.sharehouse.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SharehouseStatus {
    ENABLE("매칭 가능"),
    DISABLE("매칭 완료")
    ;

    private String value;
}
