package com.gaseng.sharehouse.service;

import com.gaseng.member.domain.Member;
import com.gaseng.member.service.MemberFindService;
import com.gaseng.sharehouse.domain.Scrap;
import com.gaseng.sharehouse.domain.Sharehouse;
import com.gaseng.sharehouse.dto.SharehouseListResponse;
import com.gaseng.sharehouse.repository.ScrapRepository;
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
}
