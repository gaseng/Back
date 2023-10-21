package com.gaseng.member.dto;

import com.gaseng.member.domain.Member;

public record AccountResponse(
        Long id,
        String name,
        String email,
        String nickname,
        String phone,
        String createdDate,
        String sex,
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