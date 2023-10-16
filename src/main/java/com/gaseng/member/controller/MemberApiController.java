package com.gaseng.member.controller;

import com.gaseng.global.common.BaseResponse;
import com.gaseng.member.dto.LoginRequest;
import com.gaseng.member.dto.LoginResponse;
import com.gaseng.member.dto.SignUpRequest;
import com.gaseng.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("/sign-up")
    public BaseResponse<Long> signUp(@RequestBody @Valid SignUpRequest request) {
        return new BaseResponse<>(memberService.signUp(request.toMember()));
    }

    @PostMapping("/login")
    public BaseResponse<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        return new BaseResponse<>(memberService.login(request.email(), request.password()));
    }
}
