package com.gaseng.checklist.controller;

import com.gaseng.checklist.dto.ChecklistRequest;
import com.gaseng.checklist.dto.ChecklistResponse;
import com.gaseng.checklist.service.ChecklistService;
import com.gaseng.global.annotation.ExtractPayload;
import com.gaseng.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@ResponseBody
@RequiredArgsConstructor
@Controller
@RequestMapping("/checklist")
public class ChecklistApiController {
    private final ChecklistService checklistService;

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "체크리스트 생성", description = "사용자가 체크리스트를 등록합니다.")
    @PostMapping(value = "")
    public BaseResponse<Long> create(@RequestParam Long memId, @RequestBody @Valid ChecklistRequest request){
        return new BaseResponse<>(checklistService.create(memId, request.toChecklist()));
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "체크리스트 수정", description = "사용자가 체크리스트를 수정합니다.")
    @PutMapping(value = "")
    public BaseResponse<Long> update(@ExtractPayload Long memId, @RequestBody @Valid ChecklistRequest request) {
        return new BaseResponse<>(checklistService.update(memId, request.toChecklist()));
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "체크리스트 조회", description = "사용자가 체크리스트를 조회합니다.")
    @GetMapping(value = "")
    public BaseResponse<ChecklistResponse> get(@ExtractPayload Long memId) {
        return new BaseResponse<>(checklistService.get(memId));
    }
}
