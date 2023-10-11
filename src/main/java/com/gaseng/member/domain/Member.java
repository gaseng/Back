package com.gaseng.member.domain;

import com.gaseng.global.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Embedded
    private Role memRole;

    @Embedded
    private MemberStatus memStatus;
}
