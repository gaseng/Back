package com.gaseng.jwt.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "token")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mem_id", nullable = false, unique = true)
    private Long memId;

    @Column(name = "refresh_token", nullable = false, unique = true)
    private String refreshToken;

    public Token(Long memId, String refreshToken) {
        this.memId = memId;
        this.refreshToken = refreshToken;
    }

    public static Token generateToken(Long memId, String refreshToken) {
        return new Token(memId, refreshToken);
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
