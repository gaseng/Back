package com.gaseng.sharehouse.service;

import com.gaseng.checklist.repository.ChecklistRepository;
import com.gaseng.file.domain.File;
import com.gaseng.file.domain.ShareFile;
import com.gaseng.file.repository.FileRepository;
import com.gaseng.file.repository.ShareFileRepository;
import com.gaseng.global.exception.BaseException;
import com.gaseng.member.domain.Member;
import com.gaseng.member.service.MemberInfoService;
import com.gaseng.sharehouse.domain.Sharehouse;
import com.gaseng.sharehouse.domain.SharehouseStatus;
import com.gaseng.sharehouse.dto.*;
import com.gaseng.sharehouse.exception.SharehouseErrorCode;
import com.gaseng.sharehouse.repository.SharehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
	private final ChecklistRepository checklistRepository;
    private final SharehouseImageService sharehouseImageService;
	private final FileRepository fileRepository;
	private final MemberInfoService memberInfoService;

    public SharehouseDetailResponse get(Long shrId) {
		Sharehouse sharehouse = findSharehouseByShrId(shrId);
		Long chkId = checklistRepository.findByMember(sharehouse.getMember()).getChkId();
    	List<String> paths = new ArrayList<>();
    	for (ShareFile shareFile : sharehouse.getShareFiles()) {
    		paths.add(shareFile.getFile().getFilePath());
    	}
    	
		return new SharehouseDetailResponse(
				shrId,
				sharehouse.getMember().getMemId(),
				chkId,
				sharehouse.getShrTitle(),
				sharehouse.getShrDescription(),
				sharehouse.getShrAddress(),
				sharehouse.getShrAddressDetail(),
				sharehouse.getShrPoster(),
				paths
		);
	}
    
    public List<SharehouseResponse> getAll(int pageSize,Long lastShrId){
    	List<SharehouseResponse> responses = new ArrayList<>();
    	List<Sharehouse> sharehouses = sharehouseRepository.findAllOrderByCreatedDateDesc();
    	
    	for (Sharehouse sharehouse : sharehouses) {
    		List<String> paths = new ArrayList<>();
        	for (ShareFile shareFile : sharehouse.getShareFiles()) {
        		paths.add(shareFile.getFile().getFilePath());
        	}
        	
        	responses.add(new SharehouseResponse(
        			sharehouse.getShrId(),
        			sharehouse.getMember().getMemId(),
        			sharehouse.getShrTitle(), 
        			sharehouse.getShrDescription(), 
        			sharehouse.getShrAddress(), 
        			sharehouse.getShrAddressDetail(), 
        			sharehouse.getShrPoster(), 
    				paths
        	));
    	}
		int lastIndex = getLastIndex(responses, lastShrId);
		return getShareListResponse(responses, lastIndex, pageSize);
	}

	private int getLastIndex(List<SharehouseResponse> sharehouses, Long lastShrId) {
		return sharehouses.indexOf(
				sharehouses.stream()
						.filter(sharehouse -> sharehouse.id().equals(lastShrId))
						.findFirst()
						.orElse(null)
		);
	}
	private List<SharehouseResponse> getShareListResponse(List<SharehouseResponse> responses, int lastIndex, int size) {
		if (lastIndex + 1 + size >= responses.size()) {
			return responses.subList(lastIndex + 1, responses.size());
		}
		return responses.subList(lastIndex + 1, lastIndex + 1 + size);
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


	public Slice<SharehouseListResponse> mySharehouse(Long memId, Pageable pageable) {
		Member member = memberInfoService.findByMemId(memId);
		return sharehouseRepository.findByMemberOrderByShrIdDesc(member, pageable);
	}
	
	public List<SharehouseListResponse> getMySharehouse(Long memId) {
		Member member = memberInfoService.findByMemId(memId);
		List<Sharehouse> list = sharehouseRepository.findByMemberOrderByCreatedDateDesc(member);
		List<SharehouseListResponse> responses = new ArrayList<SharehouseListResponse>();
		for (Sharehouse sharehouse : list) {
			responses.add(
				new SharehouseListResponse(
					sharehouse.getShrId(), 
					sharehouse.getShrTitle(), 
					sharehouse.getShrDescription(), 
					sharehouse.getShrAddress(), 
					sharehouse.getShrPoster()
				));
		}
		return responses;
	}

}
