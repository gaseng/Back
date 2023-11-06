package com.gaseng.sharehouse.dto;

import java.util.Date;
import java.util.List;

import com.gaseng.checklist.domain.CheckCigarette;
import com.gaseng.checklist.domain.CheckSleepingHabit;
import com.gaseng.checklist.domain.CheckType;

public record SharehouseDetailResponse (
        Long id,
        String name,
        CheckCigarette cigarette,
        CheckSleepingHabit habit,
        CheckType type,
        String mbti,
        String place,
        Date sleepTime,
        String title,
        String description,
        String address,
        String addressDetail,
        String poster,
        List<String> images
) {

}
