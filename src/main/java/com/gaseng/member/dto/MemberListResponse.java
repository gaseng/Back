package com.gaseng.member.dto;

import com.gaseng.member.repository.query.MemberListQueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record MemberListResponse(
        @Schema(description = "page size 크기의 member 정보(id, name)를 담은 list",
                example = "[{'memId' : 1, 'memName' : '사용자1'}]")
        List<MemberListQueryProjection> members
) {
}
