package com.gaseng.kyc.dto;

import com.gaseng.kyc.domain.KycRequire;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record KycRequireSummaryResponse (
		@Schema(description = "kyc require id", example = "1")
		Long id,

		@Schema(description = "kyc require member name", example = "홍길동")
		String name,

		@Schema(description = "kyc require created date", example = "2023-11-08T06:49:55.451533")
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
