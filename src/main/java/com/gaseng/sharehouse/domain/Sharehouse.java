package com.gaseng.sharehouse.domain;

import com.gaseng.chat.domain.ChatRoom;
import com.gaseng.file.domain.ShareFile;
import com.gaseng.global.common.BaseTimeEntity;
import com.gaseng.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "sharehouse")
public class Sharehouse extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shrId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_id", nullable = false)
    private Member member;

    private String shrTitle;

    private String shrDescription;

    private String shrAddress;

    private String shrAddressDetail;

    private String shrLatitude;

    private String shrLongitude;

    private String shrPoster;

    private SharehouseStatus shrStatus;

    @OneToMany(mappedBy = "sharehouse", orphanRemoval = true)
    private List<ShareFile> shareFiles = new ArrayList<>();

    @OneToMany(mappedBy = "sharehouse", orphanRemoval = true)
    private List<ChatRoom> chatRooms = new ArrayList<>();
}
