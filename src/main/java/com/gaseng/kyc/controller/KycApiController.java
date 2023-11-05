package com.gaseng.kyc.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gaseng.global.annotation.ExtractPayload;
import com.gaseng.global.common.BaseResponse;
import com.gaseng.kyc.dto.KycSaveRequest;
import com.gaseng.kyc.dto.KycSubmitRequest;
import com.gaseng.kyc.service.KycService;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@Api(tags = "Kyc Api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/kyc")
public class KycApiController {
	private final KycService kycService;

	@Operation(summary = "kyc 제출", description = "사용자가 kyc 인증 과정을 끝내고 제출합니다.")
	@PostMapping("/sumbit")
	public BaseResponse<Long> submit(@ExtractPayload Long memId, @RequestBody @Valid KycSubmitRequest request) throws IOException {
		return new BaseResponse<>(kycService.submit(memId, request));
	}
	
//	@Operation(summary = "kyc 정보 조회", description = "채팅방에서 상대방의 kyc를 요청합니다.")
//	@GetMapping("/info")
//	public BaseResponse<Long> info(@RequestParam Long kycrId) {
//		return new BaseResponse<>(kycService.save());
//	}
	
}
