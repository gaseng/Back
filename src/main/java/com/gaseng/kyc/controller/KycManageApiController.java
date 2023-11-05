package com.gaseng.kyc.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gaseng.global.common.BaseResponse;
import com.gaseng.kyc.dto.KycRequireResponse;
import com.gaseng.kyc.dto.KycRequireSummaryResponse;
import com.gaseng.kyc.dto.KycSaveRequest;
import com.gaseng.kyc.service.KycManageService;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@Api(tags = "Kyc Api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/kyc")
public class KycManageApiController {
	private final KycManageService kycManageService;
	
	@Operation(summary = "kyc 요청 조회", description = "kyc 요청서를 조회합니다.")
	@GetMapping("")
	public BaseResponse<KycRequireResponse> get(@RequestParam Long kycrId) {
		return new BaseResponse<>(kycManageService.get(kycrId));
	}
	
	@Operation(summary = "kyc 요청 전체 조회", description = "kyc 요청서를 조회합니다.")
	@GetMapping("/all")
	public BaseResponse<List<KycRequireSummaryResponse>> getAll() {
		return new BaseResponse<>(kycManageService.getAll());
	}
	
	@Operation(summary = "kyc 저장", description = "kyc를 저장합니다.")
	@PostMapping("")
	public BaseResponse<Long> save(@RequestParam Long kycrId, @RequestBody @Valid KycSaveRequest request) {
		return new BaseResponse<>(kycManageService.save(kycrId, request));
	}
	
}
