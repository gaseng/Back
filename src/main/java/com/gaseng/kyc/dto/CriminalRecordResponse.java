package com.gaseng.kyc.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CriminalRecordResponse {
    NOT_EXISTS_CRIMINAL_RECORD(000, "범죄 이력 없음"),
    EXISTS_CRIMINAL_RECORD(001, "범죄 이력 있음"),
    ;

    private final int status;
    private final String value;
}
