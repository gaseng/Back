package com.gaseng.member.service;

import com.gaseng.global.exception.BaseException;
import com.gaseng.member.domain.Member;
import com.gaseng.member.domain.MemberStatus;
import com.gaseng.member.domain.Role;
import com.gaseng.member.dto.AccountResponse;
import com.gaseng.member.dto.MemberListResponse;
import com.gaseng.member.exception.MemberErrorCode;
import com.gaseng.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.gaseng.member.dto.AccountResponse.toResponse;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberManageService {
    private final MemberInfoService memberInfoService;
    private final MemberRepository memberRepository;

    public MemberListResponse getMemberList(Long lastMemId) {
        memberRepository.findAllByMemRole(Role.USER);
        return new MemberListResponse();
    }

    public AccountResponse getAccount(Long memId) {
        Member member = memberInfoService.findByMemId(memId);

        return toResponse(member);
    }

    @Transactional
    public Long toNormal(Long memId) {
        Member member = memberInfoService.findByMemId(memId);
        validateIsStatusNormal(member);
        member.toNormal();

        return member.getMemId();
    }

    @Transactional
    public Long toReject(Long memId) {
        Member member = memberInfoService.findByMemId(memId);
        validateIsStatusReject(member);
        member.toReject();

        return member.getMemId();
    }

    private void validateIsStatusNormal(Member member) {
        if (member.getMemStatus().getValue().equals(MemberStatus.NORMAL)) {
            throw BaseException.type(MemberErrorCode.IS_SAME_STATUS);
        }
    }

    private void validateIsStatusReject(Member member) {
        if (member.getMemStatus().getValue().equals(MemberStatus.REJECT)) {
            throw BaseException.type(MemberErrorCode.IS_SAME_STATUS);
        }
    }
}
