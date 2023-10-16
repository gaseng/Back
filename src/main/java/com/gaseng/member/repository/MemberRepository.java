package com.gaseng.member.repository;

import com.gaseng.member.domain.Email;
import com.gaseng.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByMemEmail(Email memEmail);
    boolean existsByMemPhone(String memPhone);
    boolean existsByMemNickname(String memNickname);
}
