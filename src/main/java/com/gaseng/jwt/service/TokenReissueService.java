package com.gaseng.jwt.service;

import com.gaseng.global.exception.BaseException;
import com.gaseng.global.exception.GlobalErrorCode;
import com.gaseng.jwt.dto.TokenResponse;
import com.gaseng.jwt.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenReissueService {
    private final TokenService tokenService;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public TokenResponse reissueTokens(Long memId, String refreshToken) {
        if (!tokenService.isRefreshTokenExists(memId, refreshToken)) {
            throw BaseException.type(GlobalErrorCode.INVALID_TOKEN);
        }

        String newAccessToken = jwtTokenProvider.generateAccessToken(memId);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(memId);

        tokenService.manageRefreshTokenRotation(memId, newRefreshToken);

        return new TokenResponse(newAccessToken, newRefreshToken);
    }
}
