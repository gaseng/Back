package com.gaseng.kyc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gaseng.kyc.domain.Kyc;

public interface KycRepository extends JpaRepository<Kyc, Long> {

}
