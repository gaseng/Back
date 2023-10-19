package com.gaseng.checklist.domain;

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

    @Builder
    public Checklist(Member member, CheckSleepingHabit chkSleepingHabit, CheckCigarette chkCigarette, Date chkSleepTime, String chkMbti, String chkCallPlace, CheckType chkType) {
        this.chkSleepingHabit = chkSleepingHabit;
        this.chkCigarette = chkCigarette;
        this.chkSleepTime = chkSleepTime;
        this.chkMbti = chkMbti;
        this.chkCallPlace = chkCallPlace;
        this.chkType = chkType;
        this.member = member;
    }

    public void update(Checklist updatedChecklist) {
        this.chkSleepingHabit = updatedChecklist.getChkSleepingHabit();
        this.chkCigarette = updatedChecklist.getChkCigarette();
        this.chkSleepTime = updatedChecklist.getChkSleepTime();
        this.chkMbti = updatedChecklist.getChkMbti();
        this.chkCallPlace = updatedChecklist.getChkCallPlace();
        this.chkType = updatedChecklist.getChkType();
    }
}
