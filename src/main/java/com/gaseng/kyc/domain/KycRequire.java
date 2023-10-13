package com.gaseng.kyc.domain;

import com.gaseng.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "kyc_require")
public class KycRequire {
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

    private String kycrZip;

    private String kycrJob;

    @OneToOne(mappedBy = "kycRequire", fetch = FetchType.LAZY)
    private KycNotice kycNotice;
}
