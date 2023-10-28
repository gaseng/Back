package com.gaseng.chat.controller;

import com.gaseng.chat.dto.ChatRoomResponse;
import com.gaseng.chat.service.ChatRoomService;
import com.gaseng.global.annotation.ExtractPayload;
import com.gaseng.global.common.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "매칭 신청(채팅방 생성)", description = "사용자가 매칭을 신청하여 채팅방이 생성됩니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
            @ApiResponse(responseCode = "404", description = "회원/쉐어하우스 글을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "409", description = "이미 생성된 채팅방입니다."),
            @ApiResponse(responseCode = "500", description = "서버와의 연결에 실패했습니다.")
    })
    @PostMapping("")
    public BaseResponse<ChatRoomResponse> create(@ExtractPayload Long memId,
                                                 @Parameter(description = "sharehouse id", required = true, example = "1")
                                                 @RequestParam Long shrId) {
        return new BaseResponse<>(chatRoomService.create(memId, shrId));
    }
}
