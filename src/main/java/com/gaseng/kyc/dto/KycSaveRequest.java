package com.gaseng.kyc.dto;

import com.gaseng.global.annotation.ValidEnum;
import com.gaseng.kyc.domain.KycNotice;
import com.gaseng.kyc.domain.KycNoticeStatus;
import com.gaseng.kyc.domain.KycRequire;
import com.gaseng.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;

public record KycSaveRequest (
		@ValidEnum(enumClass = KycNoticeStatus.class)
		@Schema(description = "kyc notice status", example = "0 반려 / 1 거절 / 2 승인")
		KycNoticeStatus status,

		@Schema(description = "kyc notice description", example = "kyc 공지 내용입니다.")
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
