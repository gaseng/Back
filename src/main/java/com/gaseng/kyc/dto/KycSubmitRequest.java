package com.gaseng.kyc.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import com.gaseng.kyc.domain.Job;
import com.gaseng.kyc.domain.KycRequire;
import com.gaseng.member.domain.Member;

import io.swagger.v3.oas.annotations.media.Schema;

public record KycSubmitRequest (
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
		MultipartFile card,
		MultipartFile face
) {
	public KycRequire toKycRequire(Member member, String cardImagePath, String faceImagePath) {
		return KycRequire.builder()
				.member(member)
				.kycrName(name)
				.kycrBirth(birth)
				.kycrAddress(address)
				.kycrAddressDetail(detail)
				.kycrJob(job)
				.kycrCard(cardImagePath)
				.kycrFace(faceImagePath)
				.build();
	}
}
