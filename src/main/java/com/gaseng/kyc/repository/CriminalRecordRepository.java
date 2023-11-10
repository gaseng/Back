package com.gaseng.kyc.repository;

import com.gaseng.kyc.domain.CriminalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CriminalRecordRepository extends JpaRepository<CriminalRecord, Long> {
    boolean existsByCmrNameAndCmrPhone(String name, String phone);
}
