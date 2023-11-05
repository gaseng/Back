package com.gaseng.kyc.dto;

import java.time.LocalDate;

import com.gaseng.kyc.domain.Job;
import com.gaseng.kyc.domain.KycRequire;

import io.swagger.v3.oas.annotations.media.Schema;

public record KycRequireResponse (
		@Schema(description = "멤버 아이디 (id)", example = "1")
		Long memId,
		@Schema(description = "요청 아이디 (id)", example = "1")
		Long kycrId,
		@Schema(description = "이름 (name)", example = "김동헌")
		String name,
		@Schema(description = "생일 (birth)", example = "1998-03-16T00:00:00")
		LocalDate birth,
		@Schema(description = "주소 (address)", example = "경기도 성남시")
		String address,
		@Schema(description = "세부 주소 (detail)", example = "111호")
		String detail,
		@Schema(description = "직업 (job)", example = "학생")
		Job job,
		@Schema(description = "신분증 이미지 경로 (cardImagePath)", example = "https://image.path")
		String cardImagePath,
		@Schema(description = "얼굴 이미지 경로 (cardImagePath)", example = "https://image.path")
		String faceImagePath

) {
	public static KycRequireResponse toResponse(KycRequire entity) {
		return new KycRequireResponse(
				entity.getMember().getMemId(),
				entity.getKycrId(),
				entity.getKycrName(),
				entity.getKycrBirth(),
				entity.getKycrAddress(),
				entity.getKycrAddressDetail(),
				entity.getKycrJob(),
				entity.getKycrCard(),
				entity.getKycrFace()
			);
	}
}