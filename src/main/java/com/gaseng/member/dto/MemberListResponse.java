package com.gaseng.member.dto;

import com.gaseng.member.repository.query.MemberListQueryProjection;

import java.util.List;

public record MemberListResponse(
        List<MemberListQueryProjection> members
) {
}
