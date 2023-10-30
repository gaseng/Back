package com.gaseng.checklist.controller;

import com.gaseng.checklist.dto.ChecklistResponse;
import com.gaseng.checklist.dto.ChecklistRequest;
import com.gaseng.checklist.service.ChecklistService;
import com.gaseng.global.annotation.ExtractPayload;
import com.gaseng.global.common.BaseResponse;
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

    //@PreAuthorize("hasRole('USER')")
    @PostMapping(value = "")
    public BaseResponse<Long> create(@RequestParam Long memId, @RequestBody @Valid ChecklistRequest request){
        return new BaseResponse<>(checklistService.create(memId, request.toChecklist()));
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping(value = "")
    public BaseResponse<Long> update(@ExtractPayload Long memId, @RequestBody @Valid ChecklistRequest request) {
        return new BaseResponse<>(checklistService.update(memId, request.toChecklist()));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "")
    public BaseResponse<ChecklistResponse> get(@ExtractPayload Long memId) {
        return new BaseResponse<>(checklistService.get(memId));
    }
}
