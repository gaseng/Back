package com.gaseng.member.service;

import com.gaseng.global.exception.BaseException;
import com.gaseng.member.domain.Member;
import com.gaseng.member.domain.MemberStatus;
import com.gaseng.member.exception.MemberErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberManageService {
    private final MemberInfoService memberInfoService;

    @Transactional
    public Long toNormal(Long memId) {
        Member member = memberInfoService.findByMemId(memId);
        validateIsStatusNormal(member);
        member.toNormal();

        return member.getMemId();
    }

    private void validateIsStatusNormal(Member member) {
        if (member.getMemStatus().getValue().equals(MemberStatus.NORMAL)) {
            throw BaseException.type(MemberErrorCode.IS_SAME_STATUS);
        }
    }
}
