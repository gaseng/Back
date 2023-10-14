package com.gaseng.member.controller;

import com.gaseng.member.dto.SignUpRequest;
import com.gaseng.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Long> signUp(@RequestBody @Valid SignUpRequest request) {
        return ResponseEntity.ok(memberService.signUp(request.toMember()));
    }
}
