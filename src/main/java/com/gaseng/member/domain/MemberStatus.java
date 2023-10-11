package com.gaseng.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberStatus {
    APPROVE("승인"),
    REFUSE("거절")
    ;

    private String value;
}
