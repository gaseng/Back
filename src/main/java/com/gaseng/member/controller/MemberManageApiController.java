package com.gaseng.member.controller;

import com.gaseng.global.common.BaseResponse;
import com.gaseng.member.dto.AccountResponse;
import com.gaseng.member.dto.MemberListResponse;
import com.gaseng.member.service.MemberManageService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Member Manage Api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberManageApiController {
    private final MemberManageService memberManageService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("")
    public BaseResponse<MemberListResponse> getMemberList(@RequestParam(value = "page") int pageSize,
                                                          @RequestParam(value = "index", defaultValue = "-1", required = false) Long lastMemId) {
        return new BaseResponse<>(memberManageService.getMemberList(pageSize, lastMemId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "사용자 정보 조회", description = "사용자의 계정 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
            @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "서버와의 연결에 실패했습니다.")
    })
    @GetMapping("/{memId}")
    public BaseResponse<AccountResponse> getAccount(@Parameter(description = "memberId", required = true, example = "1") @PathVariable Long memId) {
        return new BaseResponse<>(memberManageService.getAccount(memId));
    }

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
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
