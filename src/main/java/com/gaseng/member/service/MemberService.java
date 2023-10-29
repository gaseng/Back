package com.gaseng.member.service;

import com.gaseng.file.domain.File;
import com.gaseng.file.domain.ShareFile;
import com.gaseng.global.exception.BaseException;
import com.gaseng.jwt.service.TokenService;
import com.gaseng.jwt.util.JwtTokenProvider;
import com.gaseng.member.domain.Email;
import com.gaseng.member.domain.Member;
import com.gaseng.member.domain.Password;
import com.gaseng.member.dto.LoginResponse;
import com.gaseng.member.exception.MemberErrorCode;
import com.gaseng.member.repository.MemberRepository;
import com.gaseng.sharehouse.domain.Sharehouse;
import com.gaseng.sharehouse.repository.SharehouseRepository;
import com.gaseng.sharehouse.service.SharehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.gaseng.member.domain.Password.ENCODER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberInfoService memberInfoService;
    private final SharehouseService sharehouseService;
    private final SharehouseRepository sharehouseRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenService tokenService;

    @Transactional
    public Long signUp(Member member) {
        validateDuplicateMember(member.getMemEmail(), member.getMemPhone(), member.getMemNickname());
        Member saveMember = memberRepository.save(member);

        return saveMember.getMemId();
    }

    @Transactional
    public LoginResponse login(String email, String password) {
        Email memEmail = Email.builder()
                .value(email)
                .build();
        Member member = memberInfoService.findByMemEmail(memEmail);
        comparePassword(password, member.getMemPassword());

        String accessToken = jwtTokenProvider.generateAccessToken(member.getMemId());
        String refreshToken = jwtTokenProvider.generateRefreshToken(member.getMemId());
        tokenService.manageRefreshTokenConsistency(member.getMemId(), refreshToken);

        return new LoginResponse(
                member.getMemId(),
                member.getMemEmail().getValue(),
                accessToken,
                refreshToken,
                member.getMemStatus().getValue(),
                member.getMemRole().getDescription()
        );
    }

    @Transactional
    public Long logout(Long memId) {
        tokenService.deleteRefreshTokenByMemId(memId);
        return memId;
    }

    @Transactional
    public Long signOut(Long memId) {
        Member member = memberRepository.findByMemId(memId).get();
        List<Sharehouse> shareHouses = sharehouseRepository.findByMember(member);
        for (Sharehouse shareHouse : shareHouses) {
            sharehouseService.deleteSharehouse(memId,shareHouse.getShrId());
        }
        memberRepository.delete(member);
        tokenService.deleteRefreshTokenByMemId(memId);
        return memId;
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

    private void comparePassword(String password, Password memPassword) {
        if(!memPassword.isSamePassword(password, ENCODER)) {
            throw BaseException.type(MemberErrorCode.PASSWORD_MISMATCH);
        }
    }
}
