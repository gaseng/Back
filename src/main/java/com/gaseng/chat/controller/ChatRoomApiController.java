package com.gaseng.chat.controller;

import com.gaseng.chat.dto.ChatRoomResponse;
import com.gaseng.chat.service.ChatRoomService;
import com.gaseng.global.annotation.ExtractPayload;
import com.gaseng.global.common.BaseResponse;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Chat Api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomApiController {
    private final ChatRoomService chatRoomService;

    @PostMapping("")
    public BaseResponse<ChatRoomResponse> create(@ExtractPayload Long memId, @RequestParam Long shrId) {
        return new BaseResponse<>(chatRoomService.create(memId, shrId));
    }
}
