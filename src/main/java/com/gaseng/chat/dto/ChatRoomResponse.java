package com.gaseng.chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ChatRoomResponse(
        @Schema(description = "chat_room id (index)", example = "1")
        Long chatRoomId,

        @Schema(description = "sender mem_id", example = "1")
        Long senderId,

        @Schema(description = "receiver mem_id (index)", example = "2")
        Long receiverId
) {
}
