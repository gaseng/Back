package com.gaseng.chat.repository;

import com.gaseng.chat.domain.ChatRoom;
import com.gaseng.member.domain.Member;
import com.gaseng.sharehouse.domain.Sharehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    boolean existsBySenderAndReceiverAndSharehouse(Member sender, Member receiver, Sharehouse sharehouse);
    Optional<ChatRoom> findByChatRoomId(Long chatRoomId);
}
