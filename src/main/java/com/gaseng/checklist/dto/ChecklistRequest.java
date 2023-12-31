package com.gaseng.checklist.dto;

import com.gaseng.checklist.domain.CheckCigarette;
import com.gaseng.checklist.domain.CheckSleepingHabit;
import com.gaseng.checklist.domain.CheckType;
import com.gaseng.checklist.domain.Checklist;

import java.util.Date;

public record ChecklistRequest(
        Long id,
        CheckSleepingHabit chkSleepingHabit,
        CheckCigarette chkCigarette,
        Date chkSleepTime,
        String chkMbti,
        String chkCallPlace,
        CheckType chkType
) {
    public Checklist toChecklist() {
        return Checklist.builder()
            .chkSleepingHabit(chkSleepingHabit)
            .chkCigarette(chkCigarette)
            .chkSleepTime(chkSleepTime)
            .chkMbti(chkMbti)
            .chkCallPlace(chkCallPlace)
            .chkType(chkType)
            .build();
    }
}