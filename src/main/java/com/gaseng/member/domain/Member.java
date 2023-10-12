package com.gaseng.member.domain;

import com.gaseng.checklist.domain.Checklist;
import com.gaseng.global.common.BaseTimeEntity;
import com.gaseng.sharehouse.domain.Sharehouse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "member")
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memId;

    @Embedded
    private Email memEmail;

    private String memPassword;

    private String memName;

    private String memNickname;

    private Boolean memSex;

    private String memPhone;

    private Role memRole;

    private MemberStatus memStatus;

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
    private Checklist checklist;

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Sharehouse> sharehouses = new ArrayList<>();
}
