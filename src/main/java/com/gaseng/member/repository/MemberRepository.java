package com.gaseng.member.repository;

import com.gaseng.member.domain.Email;
import com.gaseng.member.domain.Member;
import com.gaseng.member.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByMemEmail(Email memEmail);
    boolean existsByMemPhone(String memPhone);
    boolean existsByMemNickname(String memNickname);
    Optional<Member> findByMemEmail(Email memEmail);
    Optional<Member> findByMemId(Long id);
    List<Member> findAllByMemRole(Role memRole);
}
