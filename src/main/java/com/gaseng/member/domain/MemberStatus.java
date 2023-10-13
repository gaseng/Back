package com.gaseng.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberStatus {
    APPROVE("승인"),
    REJECT("반려"),
    ;

    private String value;
}