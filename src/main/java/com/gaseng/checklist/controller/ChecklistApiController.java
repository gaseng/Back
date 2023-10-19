package com.gaseng.checklist.controller;

import com.gaseng.checklist.dto.ChecklsitRequest;
import com.gaseng.checklist.service.ChecklistService;
import com.gaseng.global.annotation.ExtractPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.validation.Valid;

@ResponseBody
@RequiredArgsConstructor
@Controller
@RequestMapping("/checklist")
public class ChecklistApiController {
    private final ChecklistService checklistService;

    @PostMapping(value = "/create")
    public void create(@ExtractPayload Long memId, @RequestBody @Valid ChecklsitRequest request){
        checklistService.join(memId,request.toChecklist());
    }

    @PostMapping(value = "/update")
    public void updateChecklist(@ExtractPayload Long memId, @RequestBody @Valid ChecklsitRequest request) {
        checklistService.updateChecklist(memId, request.toChecklist());
    }

}
