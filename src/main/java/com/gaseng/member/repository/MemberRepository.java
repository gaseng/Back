package com.gaseng.member.repository;

import com.gaseng.member.domain.Email;
import com.gaseng.member.domain.Member;
import com.gaseng.member.domain.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByMemEmailAndMemStatus(Email memEmail, MemberStatus status);
    boolean existsByMemPhoneAndMemStatus(String memPhone, MemberStatus status);
    boolean existsByMemNicknameAndMemStatus(String memNickname, MemberStatus status);
}
