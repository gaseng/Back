package com.gaseng.member.service;

import com.gaseng.global.exception.BaseException;
import com.gaseng.member.domain.Email;
import com.gaseng.member.domain.Member;
import com.gaseng.member.exception.MemberErrorCode;
import com.gaseng.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberInfoService {
    private final MemberRepository memberRepository;

    public Member findByMemId(Long id) {
        return memberRepository.findByMemId(id)
                .orElseThrow(() -> BaseException.type(MemberErrorCode.MEMBER_NOT_FOUND));
    }

    public Member findByMemEmail(Email email) {
        return memberRepository.findByMemEmail(email)
                .orElseThrow(() -> BaseException.type(MemberErrorCode.MEMBER_NOT_FOUND));
    }
}