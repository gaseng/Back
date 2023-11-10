package com.gaseng.kyc.service;

import com.gaseng.global.exception.BaseException;
import com.gaseng.kyc.domain.Kyc;
import com.gaseng.kyc.domain.KycNotice;
import com.gaseng.kyc.domain.KycNoticeStatus;
import com.gaseng.kyc.domain.KycRequire;
import com.gaseng.kyc.dto.KycRequireResponse;
import com.gaseng.kyc.dto.KycRequireSummaryResponse;
import com.gaseng.kyc.dto.KycSaveRequest;
import com.gaseng.kyc.exception.KycErrorCode;
import com.gaseng.kyc.repository.CriminalRecordRepository;
import com.gaseng.kyc.repository.KycNoticeRepository;
import com.gaseng.kyc.repository.KycRequireRepository;
import com.gaseng.member.domain.Member;
import com.gaseng.member.domain.MemberStatus;
import com.gaseng.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class KycManageService {
	private final KycRequireRepository kycRequireRepository;
	private final KycNoticeRepository kycNoticeRepository;
	private final KycInterface kycInterface;
	private final CriminalRecordRepository criminalRecordRepository;
	private final MemberRepository memberRepository;
	
	public static int STATUS_ACTIVE = 0;
	public static int STATUS_INACTIVE = 1;
	public static String EXISTS_CRIMINAL_RECORD = "범죄 이력이 있습니다.";
	public static String NOT_EXISTS_CRIMINAL_RECORD = "범죄 이력이 없습니다.";
	
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
	public Long save(Long kycrId, KycSaveRequest request) throws Exception {
		
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
			kycInterface.setKyc(
					member.getMemId(), 
					entity.getKycrName(), 
					entity.getKycrBirth().toString(), 
					entity.getKycrAddress(), 
					entity.getKycrJob().getValue());
			member.setStatus(MemberStatus.APPROVE);
		}
		
		// KYC 요청건 비활성화
		KycNotice noticeEntity = request.toEntity(entity.getMember(), entity);
		entity.setStatus(STATUS_INACTIVE);
		
		return kycNoticeRepository.save(noticeEntity).getKycnId();
		
	}

	public String getCriminalRecord(Long kycrId) {

		KycRequire kycRequire = kycRequireRepository.findById(kycrId)
				.orElseThrow(() -> BaseException.type(KycErrorCode.KYC_REQUIRE_NOT_FOUND));

		String kycrName = kycRequire.getKycrName();
		String memPhone = kycRequire.getMember().getMemPhone();

		validateIsExistsMember(kycrName, memPhone);

		return validateCriminalRecord(kycrName, memPhone);

	}
	
	private void saveKyc(Member member) {
		
		String address = "";
		Kyc kyc = Kyc.builder()
				.member(member)
				.build();
		
	}

	private void validateIsExistsMember(String kycrName, String memPhone) {

		if (!memberRepository.existsByMemNameAndMemPhone(kycrName, memPhone)) {
			throw BaseException.type(KycErrorCode.KYC_REQUIRE_MEMBER_NOT_FOUND);
		}

	}

	private String validateCriminalRecord(String kycrName, String memPhone) {

		boolean isExists = criminalRecordRepository.existsByCmrNameAndCmrPhone(kycrName, memPhone);

		return isExists
				? EXISTS_CRIMINAL_RECORD
				: NOT_EXISTS_CRIMINAL_RECORD;
	}

}
