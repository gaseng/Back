package com.gaseng.checklist.service;

import com.gaseng.checklist.domain.Checklist;
import com.gaseng.checklist.repository.ChecklistRepository;
import com.gaseng.member.domain.Member;
import com.gaseng.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Transactional
public class ChecklistService {
    private final ChecklistRepository checklistRepository;
    private final MemberRepository memberRepository;

    public ChecklistService(ChecklistRepository checklistRepository, MemberRepository memberRepository) {
        this.checklistRepository = checklistRepository;
        this.memberRepository = memberRepository;
    }

    public Long join(Long memId,Checklist checklist) {
        Optional<Member> member = memberRepository.findByMemId(memId);
        checklist = Checklist.builder()
                .member(member.get())
                .chkSleepingHabit(checklist.getChkSleepingHabit())
                .chkCigarette(checklist.getChkCigarette())
                .chkSleepTime(checklist.getChkSleepTime())
                .chkMbti(checklist.getChkMbti())
                .chkCallPlace(checklist.getChkCallPlace())
                .chkType(checklist.getChkType())
                .build();
        checklistRepository.save(checklist);
        return checklist.getChkId();
    }

    public void updateChecklist(Long memId,Checklist updateChecklist) {
        Optional<Member> member = memberRepository.findByMemId(memId);
        Checklist checklist = checklistRepository.findByMember(member.get());
        checklist.update(updateChecklist);
        checklistRepository.save(checklist);
    }
}
