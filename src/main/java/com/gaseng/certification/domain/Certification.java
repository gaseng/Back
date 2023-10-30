package com.gaseng.certification.domain;

import com.gaseng.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "certification")
public class Certification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long certId;
    private Date expirationDate;
    private String  certNum;
    private String certPhone;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_id")
    private Member member;
    private Search certSearch;

    @Builder
    public Certification(Date expirationDate, String certNum, String certPhone, Member member, Search certSearch) {
        this.expirationDate = expirationDate;
        this.certNum = certNum;
        this.certPhone = certPhone;
        this.member = member;
        this.certSearch = certSearch;
    }
}

