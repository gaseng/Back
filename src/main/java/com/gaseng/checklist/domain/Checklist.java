package com.gaseng.checklist.domain;

import com.gaseng.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "checklist")
public class Checklist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chkId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_id", insertable = false, updatable = false)
    private Member member;

    private CheckSleepingHabit chkSleepingHabit;

    private CheckCigarette chkCigarette;

    private String chkSleepTime;

    private String chkMbti;

    private String chkCallPlace;

    private CheckType chkType;
}
