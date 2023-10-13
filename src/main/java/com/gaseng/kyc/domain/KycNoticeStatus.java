package com.gaseng.kyc.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KycNoticeStatus {
    REQUEST("요청"),
    REJECT("반려"),
    APPROVE("승인")
    ;

    private String value;
}
