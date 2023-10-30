package com.gaseng.chat.exception;

import com.gaseng.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ChatRoomErrorCode implements ErrorCode {
    DUPLICATE_CHAT_ROOM(HttpStatus.CONFLICT, "CHATROOM_001", "이미 생성된 채팅방입니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
