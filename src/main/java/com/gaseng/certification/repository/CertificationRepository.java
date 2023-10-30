package com.gaseng.certification.repository;

import com.gaseng.certification.domain.Certification;
import com.gaseng.certification.domain.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List;

public interface CertificationRepository extends JpaRepository<Certification, Long> {
    List<Certification> findByExpirationDateBefore(Date now);

    List<Certification> findByCertPhoneAndCertSearchOrderByExpirationDateDesc(String phone, Search search);

}
