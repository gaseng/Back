package com.gaseng.checklist.domain;

import com.gaseng.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "checklist")
public class Checklist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chkId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_id")
    private Member member;

    private CheckSleepingHabit chkSleepingHabit;

    private CheckCigarette chkCigarette;

    @Temporal(TemporalType.TIME)
    private Date chkSleepTime;

    private String chkMbti;

    private String chkCallPlace;

    private CheckType chkType;
}
