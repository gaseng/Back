package com.gaseng.kyc.controller;

import com.gaseng.global.common.BaseResponse;
import com.gaseng.kyc.dto.KycRequireResponse;
import com.gaseng.kyc.dto.KycRequireSummaryResponse;
import com.gaseng.kyc.dto.KycSaveRequest;
import com.gaseng.kyc.service.KycManageService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Kyc Api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/kyc")
public class KycManageApiController {
	private final KycManageService kycManageService;

	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "kyc 요청 조회", description = "kyc 요청서를 조회합니다.")
	@GetMapping("")
	public BaseResponse<KycRequireResponse> get(@RequestParam Long kycrId) {
		return new BaseResponse<>(kycManageService.get(kycrId));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "kyc 요청 전체 조회", description = "kyc 요청서를 조회합니다.")
	@GetMapping("/all")
	public BaseResponse<List<KycRequireSummaryResponse>> getAll() {
		return new BaseResponse<>(kycManageService.getAll());
	}

	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "kyc 저장", description = "kyc를 저장합니다.")
	@PostMapping("")
	public BaseResponse<Long> save(@RequestParam Long kycrId, @RequestBody @Valid KycSaveRequest request) throws Exception {
		return new BaseResponse<>(kycManageService.save(kycrId, request));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "범죄 이력 조회", description = "kyc 인증을 요청한 사용자의 범죄 이력을 조회합니다.")
	@GetMapping("/criminal-records/{kycrId}")
	public BaseResponse<String> getCriminalRecord(@PathVariable Long kycrId) {
		return new BaseResponse<>(kycManageService.getCriminalRecord(kycrId));
	}
	
}
