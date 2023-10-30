package com.gaseng.certification.service;

import com.gaseng.certification.domain.Search;
import com.gaseng.certification.exception.CertificationErrorCode;
import com.gaseng.global.exception.BaseException;
import com.gaseng.member.domain.Member;
import com.gaseng.certification.domain.Certification;
import com.gaseng.certification.repository.CertificationRepository;
import com.gaseng.member.domain.Password;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.gaseng.certification.domain.Search.ID;
import static com.gaseng.certification.domain.Search.PW;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MessageService {
    @Autowired
    CertificationRepository certificationRepository;
    @Value("${coolsms.api.key}")
    private String apiKey;
    @Value("${coolsms.api.secret}")
    private String apiSecretKey;
    @Value("${coolsms.api.phone}")
    private String fromPhone;
    @Transactional
    public void sendMessage(String memPhone, Member member, Search search) throws CoolsmsException {
        Message coolsms = new Message(apiKey, apiSecretKey);
        String randomNum = ranNum();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", memPhone);
        params.put("from", fromPhone);
        params.put("type", "SMS");
        params.put("text", "[가생이] 인증번호 "+randomNum+"를 입력하세요.");
        params.put("app_version", "test app 1.2");
        Certification cert = Certification.builder()
                .expirationDate(getExpirationTime())
                .certNum(randomNum)
                .certPhone(memPhone)
                .member(member)
                .certSearch(search)
                .build();
        coolsms.send(params);
        certificationRepository.save(cert);
    }

    @Transactional
    public Long searchVerifyId(String num, String phone) {
        List<Certification> certs = certificationRepository.findByCertPhoneAndCertSearchOrderByExpirationDateDesc(phone,ID);
        if (certs.get(0).getCertNum().equals(num)) {
            for (Certification cert : certs) {
                certificationRepository.delete(cert);
            }
            return certs.get(0).getMember().getMemId();
        }
        throw BaseException.type(CertificationErrorCode.CERTIFICATION_MISMATCH);
    }

    @Transactional
    public Long searchVerifyPw(String num, String phone) {
        List<Certification> certs = certificationRepository.findByCertPhoneAndCertSearchOrderByExpirationDateDesc(phone, PW);
        if (certs.get(0).getCertNum().equals(num)) {
            for (Certification cert : certs) {
                certificationRepository.delete(cert);
            }
            return certs.get(0).getMember().getMemId();
        }
        throw BaseException.type(CertificationErrorCode.CERTIFICATION_MISMATCH);
    }

    @Transactional
    @Scheduled(fixedRate = 60000)
    public void cleanupExpiredVerificationCodes() {
        Date now = new Date();
        List<Certification> certs = certificationRepository.findByExpirationDateBefore(now);
        for (Certification cert : certs) {
            certificationRepository.delete(cert);
        }
    }

    public String ranNum(){
        Random rand  = new Random();
        String numStr = "";
        for(int i=0; i<5; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr+=ran;
        }
        return numStr;
    }

    private Date getExpirationTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 3);
        return calendar.getTime();
    }


}
