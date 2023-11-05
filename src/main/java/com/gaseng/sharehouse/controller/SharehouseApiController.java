package com.gaseng.sharehouse.controller;

import com.gaseng.global.annotation.ExtractPayload;
import com.gaseng.global.common.BaseResponse;
import com.gaseng.sharehouse.domain.Sharehouse;
import com.gaseng.sharehouse.dto.*;
import com.gaseng.sharehouse.service.SharehouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@ResponseBody
@RequiredArgsConstructor
@Controller
@RequestMapping("/sharehouse")
public class SharehouseApiController {
    private final SharehouseService sharehouseService;
    
    @Operation(summary = "쉐어하우스 글 상세 조회", description = "사용자가 쉐어하우스 글을 상세 조회합니다.")
    @GetMapping(value = "")
    public BaseResponse<SharehouseDetailResponse> get(
    		@RequestParam(value = "id") Long shrId
    ) {
    	return new BaseResponse<>(sharehouseService.get(shrId));
    }
    
    @Operation(summary = "쉐어하우스 글 목록 조회", description = "사용자가 쉐어하우스 글 목록을 조회합니다.")
    @GetMapping(value = "/all")
    public BaseResponse<List<SharehouseResponse>> getAll(
            @Parameter(description = "size of page", required = true, example = "10")
            @RequestParam(value = "page") int pageSize,
            @Parameter(description = "last sharehouse id in the list (default = -1)", example = "10")
            @RequestParam(value = "index", defaultValue = "-1", required = false) Long lastShrId) {


    	return new BaseResponse<>(sharehouseService.getAll(pageSize, lastShrId));
    }

    @Operation(summary = "쉐어하우스 글 생성", description = "사용자가 쉐어하우스 글을 작성합니다.")
    @PostMapping(value = "")
    public BaseResponse<Long> create(
    		@ExtractPayload Long memId,
    		@RequestParam(value = "poster") MultipartFile poster, 
    		@Valid SharehouseRequest request
    ) throws IOException {
    	return new BaseResponse<>(sharehouseService.create(memId, poster, request));
    }

    @Operation(summary = "쉐어하우스 글 수정", description = "사용자가 쉐어하우스 글을 수정합니다.")
    @PutMapping(value = "")
    public BaseResponse<Long> update(
    		@ExtractPayload Long memId,
    		@RequestBody @Valid SharehouseUpdateRequest request
    ) {
    	return new BaseResponse<>(sharehouseService.update(memId,request));
    }
    
    @Operation(summary = "쉐어하우스 글 삭제", description = "사용자가 쉐어하우스 글을 삭제합니다.")
    @DeleteMapping(value = "")
    public BaseResponse<Long> delete(
            @ExtractPayload Long memId,
            @RequestBody @Valid SharehouseDeleteRequest request
    ) {
        return new BaseResponse<>(sharehouseService.deleteSharehouse(memId, request.id()));
    }

    @GetMapping("/my")
    public BaseResponse<Slice<SharehouseListResponse>> mySharehouse(
            @ExtractPayload Long memId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return new BaseResponse<> (sharehouseService.mySharehouse(memId, pageable));
    }
    
    @GetMapping("/my-share")
    public BaseResponse<List<SharehouseListResponse>> getMyShareHouse(
            @ExtractPayload Long memId) {
        return new BaseResponse<> (sharehouseService.getMySharehouse(memId));
    }
}
