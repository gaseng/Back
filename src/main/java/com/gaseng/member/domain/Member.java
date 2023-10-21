package com.gaseng.member.domain;

import com.gaseng.chat.domain.ChatRoom;
import com.gaseng.global.common.BaseTimeEntity;
import com.gaseng.sharehouse.domain.Sharehouse;
import lombok.AccessLevel;
import lombok.Builder;
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

    @Embedded
    private Password memPassword;

    private String memName;

    private String memNickname;

    private Sex memSex;

    private String memPhone;

    private Role memRole;

    private MemberStatus memStatus;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sharehouse> sharehouses = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<ChatRoom> chatRooms = new ArrayList<>();

    @Builder
    private Member(Email memEmail, Password memPassword, String memName, String memNickname, Sex memSex, String memPhone) {
        this.memEmail = memEmail;
        this.memPassword = memPassword;
        this.memName = memName;
        this.memNickname = memNickname;
        this.memSex = memSex;
        this.memPhone = memPhone;
        this.memRole = Role.USER;
        this.memStatus = MemberStatus.WAITING;
    }

    public static Member registerMember(
            Email memEmail, Password memPassword, String memName, String memNickname, Sex memSex, String memPhone) {
        return new Member(memEmail, memPassword, memName, memNickname, memSex, memPhone);
    }

    public void toNormal() {
        this.memStatus = MemberStatus.NORMAL;
    }
}
