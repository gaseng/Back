package com.gaseng.certification.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Search {
    ID("아이디"),
    PW("비밀번호"),
    ;

    private String value;
}
