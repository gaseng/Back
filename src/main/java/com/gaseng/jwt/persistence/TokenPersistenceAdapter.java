package com.gaseng.jwt.persistence;

public interface TokenPersistenceAdapter {
    void synchronizeRefreshToken(Long memId, String refreshToken);

    void manageRefreshTokenRotation(Long memId, String refreshToken);

    void deleteRefreshTokenByMemId(Long memId);

    boolean isExistsRefreshToken(Long memId, String refreshToken);
}