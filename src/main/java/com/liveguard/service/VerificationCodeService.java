package com.liveguard.service;

import com.liveguard.domain.VerificationCode;

public interface VerificationCodeService {

    VerificationCode save(VerificationCode verificationCode);
    VerificationCode findByCode(String code);
}
