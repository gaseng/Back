package com.gaseng.kyc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gaseng.kyc.domain.KycRequire;

public interface KycRequireRepository extends JpaRepository<KycRequire, Long> {

	List<KycRequire> findAllByKycrStatus(int status);

}
