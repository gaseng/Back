package com.gaseng.kyc.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Job {
	STUDENT("학생"),
	OFFICEWORKER("직장인"),
	UNEMPLOYED("무직")
	;
	
	private final String value;
}
