package com.gaseng.sharehouse.controller;

import com.gaseng.global.annotation.ExtractPayload;
import com.gaseng.global.common.BaseResponse;
import com.gaseng.sharehouse.dto.ScrapRequest;
import com.gaseng.sharehouse.dto.SharehouseListResponse;
import com.gaseng.sharehouse.dto.SharehouseRequest;
import com.gaseng.sharehouse.service.ScrapService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@ResponseBody
@RequiredArgsConstructor
@Controller
@RequestMapping("/scrap")
public class SharehouseScrapApiController {
    private final ScrapService scrapService;

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "내가 스크랩한 글 조회", description = "사용자가 스크랩한 글을 전체 조회합니다.")
    @GetMapping(value = "")
    public BaseResponse<List<SharehouseListResponse>> getAll(
            @ExtractPayload Long memId,
            @Parameter(description = "size of page", required = true, example = "10")
            @RequestParam(value = "page") int pageSize,
            @Parameter(description = "last scrap id in the list (default = -1)", example = "10")
            @RequestParam(value = "index", defaultValue = "-1", required = false) Long lastScrapId
    ) {
        return new BaseResponse<>(scrapService.getAll(memId, pageSize, lastScrapId));
    }


    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "스크랩 등록", description = "사용자가 스크랩합을 등록니다.")
    @PostMapping(value = "")
    public BaseResponse<Long> create(
            @ExtractPayload Long memId,
            @Valid ScrapRequest request
    ) {
        return new BaseResponse<>(scrapService.create(memId,request.id()));
    }


    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "스크랩 취소", description = "사용자가 스크랩을 취소합니다.")
    @DeleteMapping(value = "")
    public BaseResponse<Long> delete(
            @ExtractPayload Long memId,
            @Valid ScrapRequest request
    ) {
        return new BaseResponse<>(scrapService.delete(memId,request.id()));
    }
}
