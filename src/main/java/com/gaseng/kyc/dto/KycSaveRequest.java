package com.gaseng.kyc.dto;

import com.gaseng.global.annotation.ValidEnum;
import com.gaseng.kyc.domain.KycNotice;
import com.gaseng.kyc.domain.KycNoticeStatus;
import com.gaseng.kyc.domain.KycRequire;
import com.gaseng.member.domain.Member;

public record KycSaveRequest (
		@ValidEnum(enumClass = KycNoticeStatus.class)
		KycNoticeStatus status,
		String description
) {
	public KycNotice toEntity(Member member, KycRequire require) {
		return KycNotice.builder()
				.member(member)
				.kycRequire(require)
				.kycnStatus(status)
				.kycnDescription(description)
				.build();
	}
}
