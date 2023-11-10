package com.gaseng.sharehouse.service;

import com.gaseng.global.exception.BaseException;
import com.gaseng.member.domain.Member;
import com.gaseng.member.service.MemberFindService;
import com.gaseng.sharehouse.domain.Scrap;
import com.gaseng.sharehouse.domain.Sharehouse;
import com.gaseng.sharehouse.dto.SharehouseListResponse;
import com.gaseng.sharehouse.exception.SharehouseErrorCode;
import com.gaseng.sharehouse.repository.ScrapRepository;
import com.gaseng.sharehouse.repository.SharehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ScrapService {
    private final ScrapRepository scrapRepository;
    private final MemberFindService memberFindService;
    private final SharehouseRepository sharehouseRepository;

    @Transactional(readOnly = true)
    public List<SharehouseListResponse> getAll(Long memId, int pageSize, Long lastShrId) {
        Member member = memberFindService.findByMemId(memId);
        List<Scrap> scraps = scrapRepository.findByMemberOrderByScrapIdDesc(member);

        List<Sharehouse> sharehouses = new ArrayList<>();
        for (Scrap scrap : scraps) {
            sharehouses.add(scrap.getSharehouse());
        }

        List<SharehouseListResponse> scrapLists = new ArrayList<>();
        for (Sharehouse sharehouse : sharehouses) {
            scrapLists.add(new SharehouseListResponse(
                    sharehouse.getShrId(),
                    sharehouse.getShrTitle(),
                    sharehouse.getShrDescription(),
                    sharehouse.getShrAddress(),
                    sharehouse.getShrPoster()
            ));
        }

        int lastIndex = getLastIndex(scrapLists, lastShrId);

        return getScrapResponse(scrapLists, lastIndex, pageSize);
    }


    public Long create(Long memId, Long shrId){
        Member member = memberFindService.findByMemId(memId);
        Sharehouse sharehouse = findSharehouseByShrId(shrId);
        validateDuplicateScrap(member,sharehouse);

        Scrap scrap = Scrap.builder()
                .member(member)
                .sharehouse(sharehouse)
                .build();

        scrapRepository.save(scrap);

        return scrap.getScrapId();
    }


    public Long delete(Long memId, Long shrId){
        Member member = memberFindService.findByMemId(memId);
        Sharehouse sharehouse = findSharehouseByShrId(shrId);

        scrapRepository.deleteByMemberAndSharehouse(member, sharehouse);

        return shrId;
    }


    private int getLastIndex(List<SharehouseListResponse> scrapLists, Long lastShrId) {
        return scrapLists.indexOf(
                scrapLists.stream()
                        .filter(scrap -> scrap.shrId().equals(lastShrId))
                        .findFirst()
                        .orElse(null)
        );
    }


    private List<SharehouseListResponse> getScrapResponse(List<SharehouseListResponse> scrapLists, int lastIndex, int size) {
        if (lastIndex + 1 + size >= scrapLists.size()) {
            return (scrapLists.subList(lastIndex + 1, scrapLists.size()));
        }
        return (scrapLists.subList(lastIndex + 1, lastIndex + 1 + size));
    }


    public Sharehouse findSharehouseByShrId(Long shrId) {
        return sharehouseRepository.findByShrId(shrId)
                .orElseThrow(() -> BaseException.type(SharehouseErrorCode.SHAREHOUSE_NOT_FOUND));
    }

    public void validateDuplicateScrap(Member member, Sharehouse sharehouse) {
        if (scrapRepository.existsByMemberAndSharehouse(member,sharehouse)) {
            throw BaseException.type(SharehouseErrorCode.DUPLICATE_SCRAP);
        }
    }
}
