package com.gaseng.jwt.persistence;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import static com.gaseng.jwt.domain.RedisTokenKey.TOKEN_KEY;
import static java.util.concurrent.TimeUnit.SECONDS;

@Primary
@Repository
public class RedisTokenPersistenceAdapter implements TokenPersistenceAdapter {
    private final long tokenValidityInMilliseconds;
    private final StringRedisTemplate tokenStorage;
    private final ValueOperations<String, String> tokenOperations;

    public RedisTokenPersistenceAdapter(
            @Value("${jwt.refresh-token-validity}") final long tokenValidityInMilliseconds,
            final StringRedisTemplate tokenStorage
    ) {
        this.tokenValidityInMilliseconds = tokenValidityInMilliseconds;
        this.tokenStorage = tokenStorage;
        tokenOperations = tokenStorage.opsForValue();
    }

    @Override
    public void synchronizeRefreshToken(Long memId, String refreshToken) {
        tokenOperations.set(
                String.format(TOKEN_KEY.getValue(), memId),
                refreshToken,
                tokenValidityInMilliseconds,
                SECONDS
        );
    }

    @Override
    public void manageRefreshTokenRotation(Long memId, String refreshToken) {
        tokenOperations.set(
                String.format(TOKEN_KEY.getValue(), memId),
                refreshToken,
                tokenValidityInMilliseconds,
                SECONDS
        );
    }

    @Override
    public void deleteRefreshTokenByMemId(Long memId) {
        tokenStorage.delete(String.format(TOKEN_KEY.getValue(), memId));
    }

    @Override
    public boolean isExistsRefreshToken(Long memId, String refreshToken) {
        final String validToken = tokenOperations.get(String.format(TOKEN_KEY.getValue(), memId));
        return refreshToken.equals(validToken);
    }
}
