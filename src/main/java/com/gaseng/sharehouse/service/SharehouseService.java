package com.gaseng.sharehouse.service;

import com.gaseng.file.domain.File;
import com.gaseng.file.domain.ShareFile;
import com.gaseng.file.repository.FileRepository;
import com.gaseng.file.repository.ShareFileRepository;
import com.gaseng.global.exception.BaseException;
import com.gaseng.member.domain.Member;
import com.gaseng.member.service.MemberInfoService;
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

@Service
@Transactional
@RequiredArgsConstructor
public class SharehouseService {
    private final SharehouseRepository sharehouseRepository;
	private final ShareFileRepository shareFileRepository;
    private final SharehouseImageService sharehouseImageService;
	private final FileRepository fileRepository;
	private final MemberInfoService memberInfoService;

    public SharehouseResponse get(Long shrId) {
		Sharehouse sharehouse = findSharehouseByShrId(shrId);
    	
    	List<String> paths = new ArrayList<>();
    	for (ShareFile shareFile : sharehouse.getShareFiles()) {
    		paths.add(shareFile.getFile().getFilePath());
    	}
    	
		return new SharehouseResponse(
				shrId,
				sharehouse.getShrTitle(),
				sharehouse.getShrDescription(),
				sharehouse.getShrAddress(),
				sharehouse.getShrAddressDetail(),
				sharehouse.getShrPoster(),
				paths
		);
	}
    
    public List<SharehouseResponse> getAll() {
    	List<SharehouseResponse> responses = new ArrayList<>();
    	List<Sharehouse> sharehouses = sharehouseRepository.findAll();
    	
    	for (Sharehouse sharehouse : sharehouses) {
    		List<String> paths = new ArrayList<>();
        	for (ShareFile shareFile : sharehouse.getShareFiles()) {
        		paths.add(shareFile.getFile().getFilePath());
        	}
        	
        	responses.add(new SharehouseResponse(
        			sharehouse.getShrId(),
        			sharehouse.getShrTitle(), 
        			sharehouse.getShrDescription(), 
        			sharehouse.getShrAddress(), 
        			sharehouse.getShrAddressDetail(), 
        			sharehouse.getShrPoster(), 
    				paths
        	));
    	}
    	
		return responses;
	}
    
    public Long create(Long memId, MultipartFile poster, SharehouseRequest request) throws IOException {
		Member member = memberInfoService.findByMemId(memId);
		Sharehouse sharehouse = Sharehouse.builder()
        		.member(member)
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
		Sharehouse sharehouse = findSharehouseByShrId(request.id());
    	this.isAuthor(memId, sharehouse.getMember().getMemId());
		sharehouse.update(request.shrTitle(), request.shrDescription(),request.shrAddress(), request.shrAddressDetail());
    	sharehouseRepository.save(sharehouse);

		return sharehouse.getShrId();
	}
    
    private void isAuthor(Long memId, Long author) {
    	if (!memId.equals(author)) {
    		throw BaseException.type(SharehouseErrorCode.USER_ID_MISMATCH);
    	}
    }

    private void processImage(
    		MultipartFile poster, 
    		List<MultipartFile> files,
    		Sharehouse sharehouse
    ) throws IOException {
    	sharehouseImageService.uploadS3Poster(poster, sharehouse);
    	sharehouseImageService.uploadS3Images(files, sharehouse);
    }

	public Long deleteSharehouse(Long memId, Long shrId){
		Sharehouse sharehouse = findSharehouseByShrId(shrId);
		isAuthor(memId, sharehouse.getMember().getMemId());

		List<ShareFile> shareFiles = shareFileRepository.findBySharehouse(sharehouse);
		String poster = sharehouse.getShrPoster().toString();
		sharehouseImageService.deleteS3Images("sharehouse/" + poster.split("/")[4]);

		for (ShareFile shareFile : shareFiles) {
			File file = fileRepository.findById(shareFile.getFile().getFileId()).get();
			sharehouseImageService.deleteS3Images("sharehouse/" + file.getFilePath().split("/")[4]);
		}
		sharehouseRepository.delete(sharehouse);

		return shrId;
	}

	public Sharehouse findSharehouseByShrId(Long shrId) {
		return sharehouseRepository.findByShrId(shrId)
				.orElseThrow(() -> BaseException.type(SharehouseErrorCode.SHAREHOUSE_NOT_FOUND));
	}

	public List<Sharehouse> getSharehousesByCursorScroll(Long memId, int page, int size) {
		// 커서 기반의 스크롤 쿼리를 실행하여 데이터를 가져옵니다.
		List<Sharehouse> sharehouses = sharehouseRepository.getSharehousesByCursorScroll(memId, page, size);
		return sharehouses;
	}
}
