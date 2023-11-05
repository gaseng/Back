package com.gaseng.kyc.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaseng.global.exception.BaseException;
import com.gaseng.kyc.domain.Kyc;
import com.gaseng.kyc.domain.KycNotice;
import com.gaseng.kyc.domain.KycNoticeStatus;
import com.gaseng.kyc.domain.KycRequire;
import com.gaseng.kyc.dto.KycRequireResponse;
import com.gaseng.kyc.dto.KycRequireSummaryResponse;
import com.gaseng.kyc.dto.KycSaveRequest;
import com.gaseng.kyc.exception.KycErrorCode;
import com.gaseng.kyc.repository.KycNoticeRepository;
import com.gaseng.kyc.repository.KycRepository;
import com.gaseng.kyc.repository.KycRequireRepository;
import com.gaseng.member.domain.Member;
import com.gaseng.member.domain.MemberStatus;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class KycManageService {
	private final KycRepository kycRepository;
	private final KycRequireRepository kycRequireRepository;
	private final KycNoticeRepository kycNoticeRepository;
	
	public static int STATUS_ACTIVE = 0;
	public static int STATUS_INACTIVE = 1;
	
	@Transactional
	public KycRequireResponse get(Long kycrId) {
		
		KycRequire entity = kycRequireRepository.findById(kycrId)
				.orElseThrow(() -> BaseException.type(KycErrorCode.KYC_REQUIRE_NOT_FOUND));
		
		return KycRequireResponse.toResponse(entity);
		
	}
	
	@Transactional
	public List<KycRequireSummaryResponse> getAll() {
		
	    List<KycRequire> list = kycRequireRepository.findAllByKycrStatus(STATUS_ACTIVE);
		
		return list.stream()
		        .map(KycRequireSummaryResponse::toResponse)
		        .collect(Collectors.toList());
		
	}

	@Transactional
	public Long save(Long kycrId, KycSaveRequest request) {
		
		// KycRequire 객체 가져오기
		KycRequire entity = kycRequireRepository.findById(kycrId)
				.orElseThrow(() -> BaseException.type(KycErrorCode.KYC_REQUIRE_NOT_FOUND));
		
		// 이미 해결한 KYC 요청일 경우
		if (entity.getKycrStatus() == STATUS_INACTIVE) {
			throw new BaseException(KycErrorCode.KYC_REQUIRE_INACTIVE);
		}
		
		// member KYC 요청 상태에 따른 회원의 상태 변경
		Member member = entity.getMember();
		if (request.status() == KycNoticeStatus.DENIAL) {
			member.setStatus(MemberStatus.NORMAL);
		} else if (request.status() == KycNoticeStatus.REJECT) {
			member.setStatus(MemberStatus.REJECT);
		} else if (request.status() == KycNoticeStatus.APPROVE){
			saveKyc(member);
			member.setStatus(MemberStatus.APPROVE);
		}
		
		// KYC 요청건 비활성화
		KycNotice noticeEntity = request.toEntity(entity.getMember(), entity);
		entity.setStatus(STATUS_INACTIVE);
		
		return kycNoticeRepository.save(noticeEntity).getKycnId();
		
	}
	
	private void saveKyc(Member member) {
		
		// TODO: blockchain address 지정하는 부분
		String address = "";
		Kyc kyc = Kyc.builder()
				.member(member)
				.kycAddress(address)
				.build();
		
		kycRepository.save(kyc);
		
	}

}
