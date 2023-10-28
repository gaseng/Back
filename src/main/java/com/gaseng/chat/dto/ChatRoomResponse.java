package com.gaseng.chat.dto;

public record ChatRoomResponse(
        Long chatRoomId,
        Long senderId,
        Long receiverId
) {
}
