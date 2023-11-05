package com.gaseng.sharehouse.service;

import com.gaseng.member.domain.Member;
import com.gaseng.member.repository.MemberRepository;
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

    private final MemberRepository memberRepository;
    private final ScrapRepository scrapRepository;

    public List<SharehouseListResponse> getScrapAll(Long memId) {
        List<SharehouseListResponse> responses = new ArrayList<>();
        Member member = memberRepository.findByMemId(memId).get();
        List<Scrap> scraps = scrapRepository.findByMemberOrderByScrapIdDesc(member);
        List<Sharehouse> sharehouses = new ArrayList<>();
        for (Scrap scrap : scraps) {
                sharehouses.add(scrap.getSharehouse());
        }
        for(Sharehouse sharehouse: sharehouses){
            responses.add(new SharehouseListResponse(
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
