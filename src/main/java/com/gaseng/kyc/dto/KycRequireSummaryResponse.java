package com.gaseng.kyc.dto;

import java.time.LocalDateTime;

import com.gaseng.kyc.domain.KycRequire;

public record KycRequireSummaryResponse (
		Long id,
		String name,
		LocalDateTime createdAt
) {
	public static KycRequireSummaryResponse toResponse(KycRequire require) {
		return new KycRequireSummaryResponse(
				require.getKycrId(),
				require.getKycrName(),
				require.getCreatedDate()
			);
	}
}
