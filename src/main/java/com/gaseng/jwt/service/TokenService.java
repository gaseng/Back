package com.gaseng.jwt.service;

import com.gaseng.jwt.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;

    @Transactional
    public void manageRefreshTokenRotation(Long memId, String newRefreshToken) {
        tokenRepository.manageRefreshTokenRotation(memId, newRefreshToken);
    }

    @Transactional
    public void deleteRefreshTokenByMemId(Long memId) {
        tokenRepository.deleteByMemId(memId);
    }

    public boolean isRefreshTokenExists(Long memId, String refreshToken) {
        return tokenRepository.existsByMemIdAndRefreshToken(memId, refreshToken);
    }
}
