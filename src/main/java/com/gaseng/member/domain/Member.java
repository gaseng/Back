package com.gaseng.member.domain;

import com.gaseng.chat.domain.ChatRoom;
import com.gaseng.chat.domain.Message;
import com.gaseng.checklist.domain.Checklist;
import com.gaseng.file.domain.File;
import com.gaseng.global.common.BaseTimeEntity;
import com.gaseng.kyc.domain.Kyc;
import com.gaseng.kyc.domain.KycNotice;
import com.gaseng.kyc.domain.KycRequire;
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

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<File> files = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Kyc> kycs = new ArrayList<>();

    @OneToOne(mappedBy = "member", orphanRemoval = true)
    private KycNotice kycNotice;

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<KycRequire> kycRequires = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<ChatRoom> chatRooms = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Message> messages = new ArrayList<>();
}
