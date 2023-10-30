package com.gaseng.chat.service;

import com.gaseng.chat.domain.ChatRoom;
import com.gaseng.chat.dto.ChatRoomResponse;
import com.gaseng.chat.exception.ChatRoomErrorCode;
import com.gaseng.chat.repository.ChatRoomRepository;
import com.gaseng.global.exception.BaseException;
import com.gaseng.member.domain.Member;
import com.gaseng.member.service.MemberInfoService;
import com.gaseng.sharehouse.domain.Sharehouse;
import com.gaseng.sharehouse.service.SharehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final SharehouseService sharehouseService;
    private final MemberInfoService memberInfoService;

    @Transactional
    public ChatRoomResponse create(Long memId, Long shrId) {
        Member sender = memberInfoService.findByMemId(memId);
        Sharehouse sharehouse = sharehouseService.findSharehouseByShrId(shrId);

        validateDuplicateChatRoom(sender, sharehouse.getMember(), sharehouse);
        ChatRoom chatRoom = chatRoomRepository.save(ChatRoom.create(sender, sharehouse.getMember(), sharehouse));

        return new ChatRoomResponse(
                chatRoom.getChatRoomId(),
                chatRoom.getSender().getMemId(),
                chatRoom.getReceiver().getMemId()
        );
    }

    private void validateDuplicateChatRoom(Member sender, Member receiver, Sharehouse sharehouse) {
        if (chatRoomRepository.existsBySenderAndReceiverAndSharehouse(sender, receiver, sharehouse)) {
            throw BaseException.type(ChatRoomErrorCode.DUPLICATE_CHAT_ROOM);
        }
    }
}
