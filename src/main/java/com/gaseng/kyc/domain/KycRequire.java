package com.gaseng.kyc.domain;

import com.gaseng.global.common.BaseTimeEntity;
import com.gaseng.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "kyc_require")
public class KycRequire extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long kycrId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_id", nullable = false)
    private Member member;

    private String kycrName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate kycrBirth;

    private String kycrAddress;

    private String kycrAddressDetail;

    private Job kycrJob;
    
    private String kycrCard;
    
    private String kycrFace;
    
    private int kycrStatus;
    
    @Builder
    private KycRequire(Member member, String kycrName, LocalDate kycrBirth, String kycrAddress, String kycrAddressDetail, Job kycrJob, String kycrCard, String kycrFace) {
    	this.member = member;
    	this.kycrName = kycrName;
    	this.kycrBirth = kycrBirth;
    	this.kycrAddress = kycrAddress;
    	this.kycrAddressDetail = kycrAddressDetail;
    	this.kycrJob = kycrJob;
    	this.kycrCard = kycrCard;
    	this.kycrFace = kycrFace;
    }
    
    public void setStatus(int status) {
    	this.kycrStatus = status;
    }
    
}
