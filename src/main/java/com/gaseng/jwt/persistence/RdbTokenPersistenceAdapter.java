package com.gaseng.jwt.persistence;

import com.gaseng.jwt.domain.Token;
import com.gaseng.jwt.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class RdbTokenPersistenceAdapter implements TokenPersistenceAdapter {
    private final TokenRepository tokenRepository;

    @Transactional
    @Override
    public void synchronizeRefreshToken(Long memberId, String refreshToken) {
        tokenRepository.findByMemId(memberId)
                .ifPresentOrElse(
                        token -> token.updateRefreshToken(refreshToken),
                        () -> tokenRepository.save(Token.generateToken(memberId, refreshToken))
                );
    }

    @Transactional
    @Override
    public void manageRefreshTokenRotation(Long memId, String refreshToken) {
        tokenRepository.manageRefreshTokenRotation(memId, refreshToken);
    }

    @Transactional
    @Override
    public void deleteRefreshTokenByMemId(Long memId) {
        tokenRepository.deleteByMemId(memId);
    }

    @Override
    public boolean isExistsRefreshToken(Long memId, String refreshToken
    ) {
        return tokenRepository.existsByMemIdAndRefreshToken(memId, refreshToken);
    }
}