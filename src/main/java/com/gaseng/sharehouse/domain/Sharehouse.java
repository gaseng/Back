package com.gaseng.sharehouse.domain;

import com.gaseng.file.domain.ShareFile;
import com.gaseng.global.common.BaseTimeEntity;
import com.gaseng.member.domain.Member;
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

    private String shrPoster;

    private SharehouseStatus shrStatus;
    
    @OneToMany(mappedBy = "sharehouse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShareFile> shareFiles = new ArrayList<>();

    @Builder
    public Sharehouse(
            Member member,
            String shrTitle,
            String shrDescription,
            String shrAddress,
            String shrAddressDetail,
            String shrPoster,
            SharehouseStatus shrStatus
    ) {
        this.member = member;
        this.shrTitle = shrTitle;
        this.shrDescription = shrDescription;
        this.shrAddress=shrAddress;
        this.shrAddressDetail= shrAddressDetail;
        this.shrPoster=shrPoster;
        this.shrStatus=shrStatus;
    }
    
    public void updatePoster(String poster) {
    	this.shrPoster = poster;
    }
    
    public void update(
            String shrTitle,
            String shrDescription,
            String shrAddress,
            String shrAddressDetail
    ){
        this.shrTitle = shrTitle;
    	this.shrDescription = shrDescription;
        this.shrAddress=shrAddress;
        this.shrAddressDetail=shrAddressDetail;
    }


    /*public void addFile(File file) {
        this.file.add(file);

        // 게시글에 파일이 저장되어있지 않은 경우
        if(file.getBoard() != this)
            // 파일 저장
            file.setBoard(this);
    }*/
}
