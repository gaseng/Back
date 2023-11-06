package com.gaseng.chat.service;

import com.gaseng.chat.domain.ChatRoom;
import com.gaseng.chat.domain.ChatRoomStatus;
import com.gaseng.chat.domain.MemberChatRoom;
import com.gaseng.chat.dto.ChatRoomCreateResponse;
import com.gaseng.chat.dto.ChatRoomEnterResponse;
import com.gaseng.chat.dto.ChatRoomListResponse;
import com.gaseng.chat.exception.ChatRoomErrorCode;
import com.gaseng.chat.repository.ChatRoomRepository;
import com.gaseng.chat.repository.MemberChatRoomRepository;
import com.gaseng.chat.repository.query.ChatRoomListQueryProjection;
import com.gaseng.global.exception.BaseException;
import com.gaseng.global.util.LocalDateTimeFormatter;
import com.gaseng.member.domain.Member;
import com.gaseng.member.service.MemberFindService;
import com.gaseng.sharehouse.domain.Sharehouse;
import com.gaseng.sharehouse.service.SharehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final MemberChatRoomRepository memberChatRoomRepository;
    private final SharehouseService sharehouseService;
    private final MemberFindService memberFindService;
    private final ChatRoomFindService chatRoomFindService;

    @Transactional
    public ChatRoomCreateResponse createChatRoom(Long memId, Long shrId) {
        Member sender = memberFindService.findByMemId(memId);
        Sharehouse sharehouse = sharehouseService.findSharehouseByShrId(shrId);
        Member receiver = sharehouse.getMember();

        validateDuplicateChatRoom(sender, receiver, sharehouse);
        ChatRoom chatRoom = chatRoomRepository.save(ChatRoom.create(sender, receiver, sharehouse));

        memberChatRoomRepository.save(MemberChatRoom.create(chatRoom, sender));
        memberChatRoomRepository.save(MemberChatRoom.create(chatRoom, receiver));

        return new ChatRoomCreateResponse(
                chatRoom.getChatRoomId(),
                chatRoom.getSender().getMemId(),
                chatRoom.getReceiver().getMemId()
        );
    }

    public ChatRoomEnterResponse enterChatRoom(Long memId, Long chatRoomId) {
        chatRoomFindService.findByMemIdAndChatRoomId(memId, chatRoomId);
        ChatRoom chatRoom = chatRoomFindService.findByChatRoomId(chatRoomId);

        return new ChatRoomEnterResponse(
                chatRoom.getSender().getMemNickname(),
                chatRoom.getReceiver().getMemNickname(),
                chatRoom.getChatRoomStatus().getValue()
        );
    }

    @Transactional
    public Long updateMessage(Long memId, Long chatRoomId, String message) {
        chatRoomFindService.findByMemIdAndChatRoomId(memId, chatRoomId);
        ChatRoom chatRoom = chatRoomFindService.findByChatRoomId(chatRoomId);

        validateActiveChatRoom(chatRoom);
        chatRoom.updateMessage(message);

        return chatRoomId;
    }

    public List<ChatRoomListResponse> getChatRoomList(Long memId, int pageSize, Long lastChatRoomId) {
        Member member = memberFindService.findByMemId(memId);
        List<ChatRoomListQueryProjection> chatRooms = memberChatRoomRepository.findChatRoomsByMemId(memId);

        List<ChatRoomListResponse> chatRoomList = chatRooms.stream()
                .map(chatRoom -> filterMemberAndFormatTime(member, chatRoom))
                .collect(Collectors.toList());

        int lastIndex = getLastIndex(chatRoomList, lastChatRoomId);

        return getList(chatRoomList, lastIndex, pageSize);
    }

    @Transactional
    public Long deleteChatRoom(Long memId, Long chatRoomId) {
        MemberChatRoom memberChatRoom = chatRoomFindService.findByMemIdAndChatRoomId(memId, chatRoomId);
        ChatRoom chatRoom = chatRoomFindService.findByChatRoomId(chatRoomId);

        chatRoom.toInactive();
        memberChatRoomRepository.delete(memberChatRoom);

        return chatRoomId;
    }

    private void validateDuplicateChatRoom(Member sender, Member receiver, Sharehouse sharehouse) {
        if (chatRoomRepository.existsBySenderAndReceiverAndSharehouse(sender, receiver, sharehouse)) {
            throw BaseException.type(ChatRoomErrorCode.DUPLICATE_CHAT_ROOM);
        }
    }

    private void validateActiveChatRoom(ChatRoom chatRoom) {
        if (chatRoom.getChatRoomStatus().equals(ChatRoomStatus.INACTIVE)) {
            throw BaseException.type(ChatRoomErrorCode.INACTIVE_CHAT_ROOM);
        }
    }

    private ChatRoomListResponse filterMemberAndFormatTime(Member member, ChatRoomListQueryProjection chatRoom) {
        String partnerNickname = filterPartnerNickname(member, chatRoom);
        String formattedTime = formatModifiedDate(chatRoom);

        return new ChatRoomListResponse(
                chatRoom.getChatRoomId(),
                partnerNickname,
                chatRoom.getMessage(),
                formattedTime
        );
    }

    private String filterPartnerNickname(Member member, ChatRoomListQueryProjection chatRoom) {
        String senderNickname = chatRoom.getSenderNickname();
        String receiverNickname = chatRoom.getReceiverNickname();

        return member.getMemNickname().equals(senderNickname)
                ? receiverNickname
                : senderNickname;
    }

    private String formatModifiedDate(ChatRoomListQueryProjection chatRoom) {
        return LocalDateTimeFormatter.formatTimeDifference(chatRoom.getModifiedDate());
    }

    private int getLastIndex(List<ChatRoomListResponse> chatRoomList, Long lastChatRoomId) {
        return chatRoomList.indexOf(
                chatRoomList.stream()
                        .filter(chatRoom -> chatRoom.chatRoomId().equals(lastChatRoomId))
                        .findFirst()
                        .orElse(null)
        );
    }

    private List<ChatRoomListResponse> getList(List<ChatRoomListResponse> chatRoomList, int lastIndex, int size) {
        if (lastIndex + 1 + size >= chatRoomList.size()) {
            return (chatRoomList.subList(lastIndex + 1, chatRoomList.size()));
        }
        return (chatRoomList.subList(lastIndex + 1, lastIndex + 1 + size));
    }
}
