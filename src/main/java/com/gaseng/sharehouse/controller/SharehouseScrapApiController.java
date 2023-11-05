package com.gaseng.sharehouse.controller;

import com.gaseng.global.annotation.ExtractPayload;
import com.gaseng.global.common.BaseResponse;
import com.gaseng.member.dto.MemberListResponse;
import com.gaseng.sharehouse.dto.*;
import com.gaseng.sharehouse.service.ScrapService;
import com.gaseng.sharehouse.service.SharehouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@ResponseBody
@RequiredArgsConstructor
@Controller
@RequestMapping("/scrap")
public class SharehouseScrapApiController {
    private final ScrapService scrapService;

    @Operation(summary = "내가 스크랩한 글 조회", description = "사용자가 스크랩한 글 전체 조회합니다.")
    @GetMapping(value = "/all")
    public BaseResponse<List<SharehouseListResponse>> getAll(
            @ExtractPayload Long memId
    ) {
        return new BaseResponse<>(scrapService.getScrapAll(memId));
    }
}
