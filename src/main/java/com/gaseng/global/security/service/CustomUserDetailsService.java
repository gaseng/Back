package com.gaseng.global.security.service;

import com.gaseng.global.security.domain.CustomUserDetails;
import com.gaseng.global.security.dto.UserDetailsDto;
import com.gaseng.member.domain.Email;
import com.gaseng.member.domain.Member;
import com.gaseng.member.exception.MemberErrorCode;
import com.gaseng.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Email memEmail = Email.builder()
                .value(email)
                .build();
        Member member = memberRepository.findByMemEmail(memEmail)
                .orElseThrow(() -> new UsernameNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND.getMessage()));

        return new CustomUserDetails(new UserDetailsDto(member));
    }
}