package com.gaseng.sharehouse.repository;

import com.gaseng.member.domain.Member;
import com.gaseng.sharehouse.domain.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScrapRepository extends JpaRepository<Scrap,Long> {
    List<Scrap> findByMemberOrderByScrapIdDesc(Member member);

}
