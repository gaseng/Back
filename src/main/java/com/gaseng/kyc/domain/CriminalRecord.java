package com.gaseng.kyc.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "criminal_record")
public class CriminalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cmrId;

    private String cmrName;

    private String cmrPhone;

    private String cmrContent;
}
