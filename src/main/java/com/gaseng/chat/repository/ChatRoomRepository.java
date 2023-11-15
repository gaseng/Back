package com.gaseng.chat.repository;

import com.gaseng.chat.domain.ChatRoom;
import com.gaseng.chat.domain.ChatRoomStatus;
import com.gaseng.member.domain.Member;
import com.gaseng.sharehouse.domain.Sharehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    boolean existsBySenderAndReceiverAndSharehouseAndChatRoomStatus(
            Member sender, Member receiver, Sharehouse sharehouse, ChatRoomStatus chatRoomStatus);

    Optional<ChatRoom> findByChatRoomId(Long chatRoomId);
}
