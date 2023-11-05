package com.gaseng.kyc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gaseng.kyc.domain.KycNotice;

public interface KycNoticeRepository extends JpaRepository<KycNotice, Long> {

}
