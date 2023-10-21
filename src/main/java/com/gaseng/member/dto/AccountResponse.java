package com.gaseng.member.dto;

import com.gaseng.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;

public record AccountResponse(
        @Schema(description = "member id (index)", example = "1")
        Long id,

        @Schema(description = "이름 (name)", example = "뿡뿡이")
        String name,

        @Schema(description = "이메일 (email)", example = "oueya1479@naver.com")
        String email,

        @Schema(description = "닉네임 (nickname)", example = "햄뿡")
        String nickname,

        @Schema(description = "전화번호 (phone)", example = "01010102020")
        String phone,

        @Schema(description = "생성일 (created date)", example = "2023-10-21")
        String createdDate,

        @Schema(description = "성별 (sex)", example = "여자")
        String sex,

        @Schema(description = "상태 (status)", example = "대기")
        String status
) {
    public static AccountResponse toResponse (Member member) {
        return new AccountResponse(
                member.getMemId(),
                member.getMemName(),
                member.getMemEmail().getValue(),
                member.getMemNickname(),
                member.getMemPhone(),
                member.getCreatedDate().toLocalDate().toString(),
                member.getMemSex().getValue(),
                member.getMemStatus().getValue());
    }
}