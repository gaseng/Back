package com.gaseng.member.service;

import com.gaseng.certification.service.MessageService;
import com.gaseng.global.exception.BaseException;
import com.gaseng.jwt.persistence.TokenPersistenceAdapter;
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
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.gaseng.certification.domain.Search.ID;
import static com.gaseng.certification.domain.Search.PW;
import static com.gaseng.member.domain.Password.ENCODER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberFindService memberFindService;
    private final MessageService messageService;
    private final SharehouseService sharehouseService;
    private final SharehouseRepository sharehouseRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenPersistenceAdapter tokenPersistenceAdapter;

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
        Member member = memberFindService.findByMemEmail(memEmail);
        comparePassword(password, member.getMemPassword());

        String accessToken = jwtTokenProvider.generateAccessToken(member.getMemId());
        String refreshToken = jwtTokenProvider.generateRefreshToken(member.getMemId());
        tokenPersistenceAdapter.synchronizeRefreshToken(member.getMemId(), refreshToken);

        return new LoginResponse(
                member.getMemId(),
                member.getMemName(),
                member.getMemNickname(),
                member.getMemEmail().getValue(),
                accessToken,
                refreshToken,
                member.getMemStatus().getValue(),
                member.getMemRole().getDescription()
        );
    }

    @Transactional
    public Long logout(Long memId) {
        tokenPersistenceAdapter.deleteRefreshTokenByMemId(memId);
        return memId;
    }

    @Transactional
    public Long searchId(String memName, String memPhone) throws CoolsmsException {
        if (memberRepository.existsByMemPhone(memPhone)) {
            Member member = memberRepository.findByMemPhone(memPhone);
            if(member.getMemName().equals(memName)){
                messageService.sendMessage(member.getMemPhone(),member,ID);
                return member.getMemId();
            }
        }
        throw BaseException.type(MemberErrorCode.MEMBER_NOT_FOUND);
    }

    @Transactional
    public Long changePw(String memName, String memPhone, String memEmail) throws CoolsmsException {
        if (memberRepository.existsByMemPhone(memPhone)) {
            Member member = memberRepository.findByMemPhone(memPhone);
            if(member.getMemName().equals(memName)){
                if(member.getMemEmail().getValue().equals(memEmail)) {
                    messageService.sendMessage(member.getMemPhone(),member,PW);
                    return member.getMemId();
                }
            }throw BaseException.type(MemberErrorCode.MEMBER_NOT_FOUND);
        }
        throw BaseException.type(MemberErrorCode.MEMBER_NOT_FOUND);
    }

    @Transactional
    public Long pwUpdate(Long memId, String newPassword){
        Member member = memberRepository.findByMemId(memId).get();
        Password password = Password.encrypt(newPassword, ENCODER);
        member.pwUpdate(password);
        memberRepository.save(member);
        return member.getMemId();
    }

    @Transactional
    public Long signOut(Long memId) {
        Member member = memberRepository.findByMemId(memId).get();
        List<Sharehouse> shareHouses = sharehouseRepository.findByMember(member);
        for (Sharehouse shareHouse : shareHouses) {
            sharehouseService.deleteSharehouse(memId,shareHouse.getShrId());
        }
        memberRepository.delete(member);
        tokenPersistenceAdapter.deleteRefreshTokenByMemId(memId);
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
