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
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Long signUp(Member member) {
        validateDuplicateMember(member.getMemEmail(), member.getMemPhone(), member.getMemNickname());
        Member saveMember = memberRepository.save(member);

        return saveMember.getMemId();
    }

    private void validateDuplicateMember(Email memEmail, String memPhone, String memNickname) {
        validateDuplicateEmail(memEmail);
        validateDuplicatePhone(memPhone);
        validateDuplicateNickname(memNickname);
    }

    private void validateDuplicateEmail(Email memEmail) {
        if (memberRepository.existsByMemEmail(memEmail)) {
            throw BaseException.type(MemberErrorCode.DUPLICATE_EMAIL);
        }
    }

    private void validateDuplicatePhone(String memPhone) {
        if (memberRepository.existsByMemPhone(memPhone)) {
            throw BaseException.type(MemberErrorCode.DUPLICATE_PHONE);
        }
    }

    private void validateDuplicateNickname(String memNickname) {
        if (memberRepository.existsByMemNickname(memNickname)) {
            throw BaseException.type(MemberErrorCode.DUPLICATE_NICKNAME);
        }
    }
}
