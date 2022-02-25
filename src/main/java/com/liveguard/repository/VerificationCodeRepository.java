package com.liveguard.repository;

import com.liveguard.domain.EmailSendStatus;
import com.liveguard.domain.VerificationCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface VerificationCodeRepository extends CrudRepository<VerificationCode, Integer> {

    VerificationCode findByUserId(Long id);

    Set<VerificationCode> findByEmailSendStatus(EmailSendStatus status);
}
