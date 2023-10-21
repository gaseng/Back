package com.gaseng.member.controller;

import com.gaseng.global.common.BaseResponse;
import com.gaseng.member.service.MemberManageService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Member Manage Api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberManageApiController {
    private final MemberManageService memberManageService;

    @Operation(summary = "사용자 상태 NORMAL로 변경", description = "사용자의 상태를 NORMAL로 변경합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
            @ApiResponse(responseCode = "409", description = "요청과 동일한 상태입니다."),
            @ApiResponse(responseCode = "500", description = "서버와의 연결에 실패했습니다.")
    })
    @PatchMapping("/normal/{memId}")
    public BaseResponse<Long> toNormal(@Parameter(description = "memberId", required = true, example = "1") @PathVariable Long memId) {
        return new BaseResponse<>(memberManageService.toNormal(memId));
    }

    @Operation(summary = "사용자 상태 REJECT로 변경", description = "사용자의 상태를 REJECT로 변경합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
            @ApiResponse(responseCode = "409", description = "요청과 동일한 상태입니다."),
            @ApiResponse(responseCode = "500", description = "서버와의 연결에 실패했습니다.")
    })
    @PatchMapping("/reject/{memId}")
    public BaseResponse<Long> toReject(@Parameter(description = "memberId", required = true, example = "1") @PathVariable Long memId) {
        return new BaseResponse<>(memberManageService.toReject(memId));
    }
}
