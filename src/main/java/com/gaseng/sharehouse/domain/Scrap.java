package com.gaseng.sharehouse.domain;

import com.gaseng.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "scrap_sharehouse")
public class Scrap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scrapId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shr_id", nullable = false)
    private Sharehouse sharehouse;
}
