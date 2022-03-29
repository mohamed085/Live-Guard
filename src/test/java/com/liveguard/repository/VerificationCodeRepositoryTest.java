package com.liveguard.repository;

import com.liveguard.domain.EmailSendStatus;
import com.liveguard.domain.VerificationCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class VerificationCodeRepositoryTest {

    @Autowired
    VerificationCodeRepository verificationCodeRepository;

    @Test
    void findById() {
        Long id = 12L;
        VerificationCode verificationCode = verificationCodeRepository.getById(id);

        System.out.println(verificationCode);
        assertEquals(id, verificationCode.getId());

    }

    @Test
    void updateVerificationCodeStatus() {
        verificationCodeRepository.updateVerificationCodeStatus(12L, EmailSendStatus.SEND);

        Long id = 12L;
        VerificationCode verificationCode = verificationCodeRepository.getById(id);

        System.out.println(verificationCode);
        assertEquals(verificationCode.getEmailSendStatus(), EmailSendStatus.SEND);
    }

    @Test
    void findByCode() {
        String code = "ofrqsWIzqIdFg5D2eGO6BErbglDgEJnuvdkviSzuXbhPiKzyUgdZRn6dbUDetW38";
        VerificationCode verificationCode = verificationCodeRepository.findByCode(code).get();

        System.out.println(verificationCode);
        assertEquals(code, verificationCode.getCode());
    }


}