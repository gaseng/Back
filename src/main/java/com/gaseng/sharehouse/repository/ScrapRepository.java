package com.gaseng.sharehouse.repository;

import com.gaseng.member.domain.Member;
import com.gaseng.sharehouse.domain.Scrap;
import com.gaseng.sharehouse.domain.Sharehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap,Long> {

    List<Scrap> findByMemberOrderByScrapIdDesc(Member member);
    boolean existsByMemberAndSharehouse(Member member, Sharehouse share);
    boolean existsByMemberMemIdAndSharehouseShrId(Long memId, Long shrId);
	Optional<Scrap> findByMemberMemIdAndSharehouseShrId(Long memId, Long shrId);

}
