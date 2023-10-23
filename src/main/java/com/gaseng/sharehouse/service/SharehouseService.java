package com.gaseng.sharehouse.service;

import com.gaseng.file.domain.ShareFile;
import com.gaseng.global.exception.BaseException;
import com.gaseng.member.domain.Member;
import com.gaseng.member.repository.MemberRepository;
import com.gaseng.sharehouse.domain.Sharehouse;
import com.gaseng.sharehouse.domain.SharehouseStatus;
import com.gaseng.sharehouse.dto.SharehouseRequest;
import com.gaseng.sharehouse.dto.SharehouseResponse;
import com.gaseng.sharehouse.dto.SharehouseUpdateRequest;
import com.gaseng.sharehouse.exception.SharehouseErrorCode;
import com.gaseng.sharehouse.repository.SharehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

@Service
@Transactional
@RequiredArgsConstructor
public class SharehouseService {
    private final SharehouseRepository sharehouseRepository;
    private final MemberRepository memberRepository;
    private final SharehouseImageService sharehouseImageService;
    
    public SharehouseResponse get(Long shrId) {
    	Optional<Sharehouse> sharehouse = sharehouseRepository.findByShrId(shrId);
    	Sharehouse sharehouseEntity = sharehouse.get();
    	
    	List<String> paths = new ArrayList<>();
    	for (ShareFile shareFile : sharehouseEntity.getShareFiles()) {
    		paths.add(shareFile.getFile().getFilePath());
    	}
    	
		return new SharehouseResponse(
				sharehouseEntity.getShrTitle(), 
				sharehouseEntity.getShrDescription(), 
				sharehouseEntity.getShrAddress(), 
				sharehouseEntity.getShrAddressDetail(), 
				sharehouseEntity.getShrPoster(), 
				paths
		);
	}
    
    public Long create(Long memId, MultipartFile poster, SharehouseRequest request) throws IOException {
        Optional<Member> member = memberRepository.findByMemId(memId);
        
        Sharehouse sharehouse = Sharehouse.builder()
        		.member(member.get())
                .shrTitle(request.shrTitle())
                .shrDescription(request.shrDescription())
                .shrAddress(request.shrAddress())
                .shrAddressDetail(request.shrAddressDetail())
                .shrPoster("")
                .shrStatus(SharehouseStatus.ENABLE)
                .build();

        Sharehouse savedSharehouse = sharehouseRepository.save(sharehouse);
        this.processImage(poster, request.files(), savedSharehouse);
        
        return sharehouse.getShrId();
    }
    
    public Long update(Long memId, SharehouseUpdateRequest request) {
    	Optional<Sharehouse> sharehouse = sharehouseRepository.findByShrId(request.id());
    	Sharehouse sharehouseEntity = sharehouse.get();
    	
    	this.isAuthor(memId, sharehouseEntity.getMember().getMemId());
    	
    	sharehouseEntity.updateTitleandDescription(request.title(), request.description());
    	
		return sharehouseEntity.getShrId();
	}
    
    private void isAuthor(Long memId, Long author) {
    	if (memId != author) {
    		throw BaseException.type(SharehouseErrorCode.USER_ID_MISMATCH);
    	}
    }

    public void updateChecklist(Long memId,Sharehouse updateSharehouse) {
        Optional<Member> member = memberRepository.findByMemId(memId);
        Sharehouse sharehouse = sharehouseRepository.findByMember(member.get());
        sharehouse.update(updateSharehouse);
        sharehouseRepository.save(sharehouse);
    }
    
    private void processImage(
    		MultipartFile poster, 
    		List<MultipartFile> files,
    		Sharehouse sharehouse
    ) throws IOException {
    	sharehouseImageService.uploadS3Poster(poster, sharehouse);
    	sharehouseImageService.uploadS3Images(files, sharehouse);
    }
    
}