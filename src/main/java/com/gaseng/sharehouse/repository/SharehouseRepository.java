package com.gaseng.sharehouse.repository;

import com.gaseng.member.domain.Member;
import com.gaseng.sharehouse.domain.Sharehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SharehouseRepository extends JpaRepository<Sharehouse,Long> {
    Optional<Sharehouse> findByShrId(Long shrId);
    List<Sharehouse> findByMember(Member member);

    @Query(value = "SELECT * FROM sharehouse WHERE mem_id = :memId ORDER BY created_date DESC LIMIT :size OFFSET :page * :size", nativeQuery = true)
    List<Sharehouse> getSharehousesByCursorScroll(Long memId, @Param("page") int page, @Param("size") int size);

}
