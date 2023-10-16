package com.gaseng.global.security.util;

import com.gaseng.global.exception.BaseException;
import com.gaseng.global.exception.GlobalErrorCode;
import com.gaseng.global.security.domain.CustomUserDetails;
import com.gaseng.global.security.dto.UserDetailsDto;
import com.gaseng.jwt.util.JwtTokenProvider;
import com.gaseng.member.domain.Member;
import com.gaseng.member.exception.MemberErrorCode;
import com.gaseng.member.service.MemberInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberInfoService memberInfoService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            if (jwtTokenProvider.validateToken(token)) {
                Long memId = jwtTokenProvider.getId(token);
                Member member = memberInfoService.findByMemId(memId);

                if (member != null) {
                    CustomUserDetails customUserDetails = new CustomUserDetails(new UserDetailsDto(member));
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            customUserDetails, "", customUserDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                } else {
                    throw BaseException.type(MemberErrorCode.MEMBER_NOT_FOUND);
                }
            } else {
                throw BaseException.type(GlobalErrorCode.INVALID_TOKEN);
            }
        }

        filterChain.doFilter(request, response);
    }
}