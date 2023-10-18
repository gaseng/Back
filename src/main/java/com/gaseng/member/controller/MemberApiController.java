package com.gaseng.member.controller;

import com.gaseng.global.annotation.ExtractPayload;
import com.gaseng.global.common.BaseResponse;
import com.gaseng.member.dto.LoginRequest;
import com.gaseng.member.dto.LoginResponse;
import com.gaseng.member.dto.SignUpRequest;
import com.gaseng.member.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "Member Api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberApiController {
    private final MemberService memberService;

    @Operation(summary = "회원가입", description = "사용자가 회원가입을 진행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공", content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "400", description = "이메일/비밀번호 형식이 올바르지 않습니다.", content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "409", description = "이미 등록된 이메일/전화번호/닉네임입니다.", content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    @PostMapping("/sign-up")
    public BaseResponse<Long> signUp(@RequestBody @Valid SignUpRequest request) {
        return new BaseResponse<>(memberService.signUp(request.toMember()));
    }

    @PostMapping("/login")
    public BaseResponse<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        return new BaseResponse<>(memberService.login(request.email(), request.password()));
    }

    @PostMapping("/logout")
    public BaseResponse<Long> logout(@ExtractPayload Long memId) {
        return new BaseResponse<>(memberService.logout(memId));
    }
}
