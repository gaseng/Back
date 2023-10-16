package com.gaseng.jwt.controller;

import com.gaseng.global.annotation.ExtractPayload;
import com.gaseng.global.annotation.ExtractToken;
import com.gaseng.global.common.BaseResponse;
import com.gaseng.jwt.dto.TokenResponse;
import com.gaseng.jwt.service.TokenReissueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
public class TokenApiController {
    private final TokenReissueService tokenReissueService;

    @PostMapping("/reissue")
    public BaseResponse<TokenResponse> reissueTokens(@ExtractPayload Long memId, @ExtractToken String refreshToken) {
        TokenResponse tokenResponse = tokenReissueService.reissueTokens(memId, refreshToken);
        return new BaseResponse<>(tokenResponse);
    }
}