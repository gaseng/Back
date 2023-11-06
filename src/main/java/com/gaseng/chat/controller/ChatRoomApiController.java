package com.gaseng.chat.controller;

import com.gaseng.chat.dto.ChatRoomCreateResponse;
import com.gaseng.chat.dto.ChatRoomEnterResponse;
import com.gaseng.chat.dto.ChatRoomListResponse;
import com.gaseng.chat.service.ChatRoomService;
import com.gaseng.global.annotation.ExtractPayload;
import com.gaseng.global.common.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Chat Api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomApiController {
    private final ChatRoomService chatRoomService;

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "매칭 신청(채팅방 생성)", description = "사용자가 매칭을 신청하여 채팅방이 생성됩니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
            @ApiResponse(responseCode = "404", description = "회원/쉐어하우스 글을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "409", description = "이미 생성된 채팅방입니다."),
            @ApiResponse(responseCode = "500", description = "서버와의 연결에 실패했습니다.")
    })
    @PostMapping("")
    public BaseResponse<ChatRoomCreateResponse> createChatRoom(@ExtractPayload Long memId,
                                                               @Parameter(description = "sharehouse id", required = true, example = "1")
                                                               @RequestParam Long shrId) {
        return new BaseResponse<>(chatRoomService.createChatRoom(memId, shrId));
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "채팅방 접속", description = "사용자가 채팅방에 접속하여 채팅을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
            @ApiResponse(responseCode = "404", description = "채팅방을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "서버와의 연결에 실패했습니다.")
    })
    @PostMapping("/{chatRoomId}")
    public BaseResponse<ChatRoomEnterResponse> enterChatRoom(@ExtractPayload Long memId,
                                                             @Parameter(description = "chatroom id", required = true, example = "1")
                                                             @PathVariable Long chatRoomId) {
        return new BaseResponse<>(chatRoomService.enterChatRoom(memId, chatRoomId));
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "채팅 메세지 등록", description = "채팅방의 마지막 메세지를 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
            @ApiResponse(responseCode = "400", description = "채팅이 불가능한 채팅방입니다."),
            @ApiResponse(responseCode = "404", description = "채팅방을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "서버와의 연결에 실패했습니다.")
    })
    @PatchMapping("/{chatRoomId}")
    public BaseResponse<Long> updateMessage(@ExtractPayload Long memId,
                                            @Parameter(description = "chatroom id", required = true, example = "1")
                                            @PathVariable Long chatRoomId,
                                            @Parameter(description = "message content", required = true, example = "hello")
                                            @RequestBody String message) {
        return new BaseResponse<>(chatRoomService.updateMessage(memId, chatRoomId, message));
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "채팅방 전체 조회", description = "사용자의 전체 채팅방 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
            @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "서버와의 연결에 실패했습니다.")
    })
    @GetMapping("")
    public BaseResponse<List<ChatRoomListResponse>> getChatRoomList(@ExtractPayload Long memId,
                                                                    @Parameter(description = "size of page", required = true, example = "10")
                                                                    @RequestParam(value = "page") int pageSize,
                                                                    @Parameter(description = "last chatroom id in the list (default = -1)", example = "10")
                                                                    @RequestParam(value = "index", defaultValue = "-1", required = false) Long lastChatRoomId) {
        return new BaseResponse<>(chatRoomService.getChatRoomList(memId, pageSize, lastChatRoomId));
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "채팅방 나가기", description = "사용자가 채팅방 나가기를 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
            @ApiResponse(responseCode = "404", description = "채팅방을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "서버와의 연결에 실패했습니다.")
    })
    @DeleteMapping("/{chatRoomId}")
    public BaseResponse<Long> deleteChatRoom(@ExtractPayload Long memId,
                                             @Parameter(description = "chatroom id", required = true, example = "1")
                                             @PathVariable Long chatRoomId) {
        return new BaseResponse<>(chatRoomService.deleteChatRoom(memId, chatRoomId));
    }
}
