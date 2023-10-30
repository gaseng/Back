package com.gaseng.member.controller;

import com.gaseng.global.annotation.ExtractPayload;
import com.gaseng.global.common.BaseResponse;
import com.gaseng.member.dto.*;
import com.gaseng.member.service.MemberService;
import com.gaseng.certification.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "Member Api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberApiController {
    private final MemberService memberService;
    private final MessageService messageService;

    @Operation(summary = "회원가입", description = "사용자가 회원가입을 진행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
            @ApiResponse(responseCode = "400", description = "이메일/비밀번호 형식이 올바르지 않습니다."),
            @ApiResponse(responseCode = "409", description = "이미 등록된 이메일/전화번호/닉네임입니다."),
            @ApiResponse(responseCode = "500", description = "서버와의 연결에 실패했습니다.")
    })
    @PostMapping("/sign-up")
    public BaseResponse<Long> signUp(@RequestBody @Valid SignUpRequest request) {
        return new BaseResponse<>(memberService.signUp(request.toMember()));
    }

    @Operation(summary = "로그인", description = "사용자가 로그인을 진행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
            @ApiResponse(responseCode = "401", description = "비밀번호가 일치하지 않습니다."),
            @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "서버와의 연결에 실패했습니다.")
    })
    @PostMapping("/login")
    public BaseResponse<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        return new BaseResponse<>(memberService.login(request.email(), request.password()));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "로그아웃", description = "사용자가 로그아웃을 진행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
            @ApiResponse(responseCode = "401", description = "자격 증명이 이루어지지 않았습니다."),
            @ApiResponse(responseCode = "500", description = "서버와의 연결에 실패했습니다.")
    })

    @PostMapping("/logout")
    public BaseResponse<Long> logout(@ExtractPayload Long memId) {
        return new BaseResponse<>(memberService.logout(memId));
    }

    @Operation(summary = "아이디찾기", description = "사용자가 아이디 찾기를 진행합니다.")
    @PostMapping("/search-id")
    public BaseResponse<Long> searchId(@RequestBody @Valid SearchIdRequest request) throws CoolsmsException {
        return new BaseResponse<>(memberService.searchId(request.name(),request.phone()));
    }

    @Operation(summary = "인증번호 인증", description = "사용자가 인증번호 인증을 진행합니다.")
    @PostMapping("/search-id/verify")
    public BaseResponse<Long> searchVerify(@RequestBody @Valid CertificationRequest request){
        return new BaseResponse<>(messageService.searchVerifyId(request.num(),request.phone()));
    }

    @Operation(summary = "비밀번호 변경", description = "사용자가 비밀번호 변경 진행합니다.")
    @PostMapping("/change-pw")
    public BaseResponse<Long> changePw(@RequestBody @Valid ChangePwRequest request) throws CoolsmsException {
        return new BaseResponse<>(memberService.changePw(request.name(),request.phone(),request.email()));
    }

    @Operation(summary = "인증번호 인증", description = "사용자가 인증번호 인증을 진행합니다.")
    @PostMapping("/change-pw/verify")
    public BaseResponse<Long> changeVerify(@RequestBody @Valid CertificationRequest request){
        return new BaseResponse<>(messageService.searchVerifyPw(request.num(),request.phone()));
    }

    @Operation(summary = "비밀번호 변경", description = "사용자가 비밀번호 변경을 진행합니다.")
    @PostMapping("/pw-update")
    public BaseResponse<Long> pwUpdate(@RequestBody @Valid PwUpdateRequest request) {
        return new BaseResponse<>(memberService.pwUpdate(request.id(), request.password()));
    }
    @Operation(summary = "회원탈퇴", description = "사용자가 회원탈퇴를 진행합니다.")
    @PostMapping("/sign-out")
    public BaseResponse<Long> signOut(@ExtractPayload Long memId){
        return new BaseResponse<>(memberService.signOut(memId));

    }
}
