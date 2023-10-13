package com.gaseng.kyc.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KycNoticeStatus {
    DENIAL("반려"),
    REJECT("거절"),
    APPROVE("승인")
    ;

    private String value;
}
