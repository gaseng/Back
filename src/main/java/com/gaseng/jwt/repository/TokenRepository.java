package com.gaseng.jwt.repository;

import com.gaseng.jwt.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Token t " +
            "SET t.refreshToken = :refreshToken " +
            "WHERE t.memId = :memId ")
    void manageRefreshTokenRotation(@Param("memId") Long memId, @Param("refreshToken") String newRefreshToken);

    boolean existsByMemIdAndRefreshToken(Long memId, String refreshToken);
    void deleteByMemId(Long memId);
    Optional<Token> findByMemId(Long memId);
}
