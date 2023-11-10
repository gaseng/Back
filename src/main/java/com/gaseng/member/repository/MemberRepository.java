package com.gaseng.member.repository;

import com.gaseng.member.domain.Email;
import com.gaseng.member.domain.Member;
import com.gaseng.member.domain.Role;
import com.gaseng.member.repository.query.MemberListQueryProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByMemEmail(Email memEmail);
    boolean existsByMemPhone(String memPhone);
    boolean existsByMemNickname(String memNickname);
    Optional<Member> findByMemEmail(Email memEmail);
    Optional<Member> findByMemId(Long id);
    Member findByMemPhone(String memPhone);
    boolean existsByMemNameAndMemPhone(String kycrName, String memPhone);

    @Query("SELECT m.memId AS memId, m.memName AS memName FROM Member m WHERE m.memRole = :memRole ")
    List<MemberListQueryProjection> findMemberByMemRole(@Param("memRole") Role memRole);
}
