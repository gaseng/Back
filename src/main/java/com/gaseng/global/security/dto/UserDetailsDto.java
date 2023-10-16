package com.gaseng.global.security.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gaseng.member.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserDetailsDto {
    private Long memId;
    @JsonIgnore
    private String memEmail;
    @JsonIgnore
    private String memPassword;
    private String memName;
    private List<String> memRole;

    public UserDetailsDto(Member member) {
        this.memId = member.getMemId();
        this.memEmail = member.getMemEmail().getValue();
        this.memPassword = member.getMemPassword().getValue();
        this.memName = member.getMemName();
        this.memRole = List.of(member.getMemRole().getAuthority());
    }
}