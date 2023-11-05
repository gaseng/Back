package com.gaseng.sharehouse.repository;

import com.gaseng.member.domain.Member;
import com.gaseng.sharehouse.domain.Sharehouse;
import com.gaseng.sharehouse.dto.SharehouseListResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SharehouseRepository extends JpaRepository<Sharehouse,Long> {
    Optional<Sharehouse> findByShrId(Long shrId);
    List<Sharehouse> findByMember(Member member);
    List<Sharehouse> findByMemberOrderByCreatedDateDesc(Member member);
    @Query("SELECT s FROM Sharehouse s ORDER BY s.createdDate DESC")
    List<Sharehouse> findAllOrderByCreatedDateDesc();

    Slice<SharehouseListResponse> findByMemberOrderByShrIdDesc(Member member, Pageable pageable);

}
