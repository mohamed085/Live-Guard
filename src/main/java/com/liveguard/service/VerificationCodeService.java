package com.liveguard.service;

import com.liveguard.domain.EmailSendStatus;
import com.liveguard.domain.User;
import com.liveguard.domain.VerificationCode;

import java.util.Set;

public interface VerificationCodeService {

    VerificationCode generateCode(User user);

    VerificationCode save(VerificationCode code);

    VerificationCode findByUserId(Long id);

    void updateEmailSendStatus(Long userId, EmailSendStatus status);

    Set<VerificationCode> findUnsendedEmail();
}
