package com.gaseng.kyc.controller;

import com.gaseng.global.common.BaseResponse;
import com.gaseng.kyc.dto.KycRequireResponse;
import com.gaseng.kyc.dto.KycRequireSummaryResponse;
import com.gaseng.kyc.dto.KycSaveRequest;
import com.gaseng.kyc.service.KycManageService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
			@ApiResponse(responseCode = "404", description = "KYC 요청을 찾을 수 없습니다."),
			@ApiResponse(responseCode = "500", description = "서버와의 연결에 실패했습니다.")
	})
	@GetMapping("")
	public BaseResponse<KycRequireResponse> get(
			@Parameter(description = "kyc require id", required = true, example = "1")
			@RequestParam Long kycrId
	) {
		return new BaseResponse<>(kycManageService.get(kycrId));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "kyc 요청 전체 조회", description = "kyc 요청서 목록을 조회합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
			@ApiResponse(responseCode = "500", description = "서버와의 연결에 실패했습니다.")
	})
	@GetMapping("/all")
	public BaseResponse<List<KycRequireSummaryResponse>> getAll() {
		return new BaseResponse<>(kycManageService.getAll());
	}

	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "kyc 저장", description = "kyc를 저장합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
			@ApiResponse(responseCode = "404", description = "KYC 요청을 찾을 수 없습니다. / 비활성화된 KYC 요청입니다."),
			@ApiResponse(responseCode = "500", description = "서버와의 연결에 실패했습니다.")
	})
	@PostMapping("")
	public BaseResponse<Long> save(
			@Parameter(description = "kyc require id", required = true, example = "1")
			@RequestParam Long kycrId,
			@RequestBody @Valid KycSaveRequest request
	) throws Exception {
		return new BaseResponse<>(kycManageService.save(kycrId, request));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "범죄 이력 조회", description = "kyc 인증을 요청한 사용자의 범죄 이력을 조회합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
			@ApiResponse(responseCode = "404", description = "KYC 요청을 찾을 수 없습니다. / 일치하는 회원이 없습니다."),
			@ApiResponse(responseCode = "500", description = "서버와의 연결에 실패했습니다.")
	})
	@GetMapping("/criminal-records/{kycrId}")
	public BaseResponse<Integer> getCriminalRecord(
			@Parameter(description = "kyc require id", required = true, example = "1")
			@PathVariable Long kycrId
	) {
		return new BaseResponse<>(kycManageService.getCriminalRecord(kycrId));
	}
	
}
