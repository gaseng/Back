package com.gaseng.member.controller;

import com.gaseng.global.common.BaseResponse;
import com.gaseng.member.service.MemberManageService;
import io.swagger.annotations.Api;
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

    @PatchMapping("/normal/{memId}")
    public BaseResponse<Long> toNormal(@PathVariable Long memId) {
        return new BaseResponse<>(memberManageService.toNormal(memId));
    }
}
