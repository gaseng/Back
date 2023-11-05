package com.gaseng.kyc.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaseng.kyc.domain.KycRequire;
import com.gaseng.kyc.dto.KycSubmitRequest;
import com.gaseng.kyc.repository.KycRequireRepository;
import com.gaseng.member.domain.Member;
import com.gaseng.member.domain.MemberStatus;
import com.gaseng.member.service.MemberFindService;
import com.gaseng.sharehouse.service.S3Uploader;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class KycService {
	private final MemberFindService memberfindService;
	private final KycRequireRepository kycRequireRepository;
	private final S3Uploader s3Uploader;
	
	private static final String IMAGE_PATH = "kyc";
	
	@Transactional
	public Long submit(Long memId, KycSubmitRequest request) throws IOException {
		
		// member 가져오기
		// member status 대기로 변경
		Member member = memberfindService.findByMemId(memId);
		member.setStatus(MemberStatus.WAITING);
		
		// 신분증, 얼굴사진 s3에 업로드
		String cardImagePath = s3Uploader.upload(request.card(), IMAGE_PATH);
		String faceImagePath = s3Uploader.upload(request.face(), IMAGE_PATH);
		
		KycRequire entity = request.toKycRequire(member, cardImagePath, faceImagePath);
		return kycRequireRepository.save(entity).getKycrId();
	}
	
}
