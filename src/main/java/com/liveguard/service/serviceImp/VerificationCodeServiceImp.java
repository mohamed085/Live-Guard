package com.liveguard.service.serviceImp;

import com.liveguard.domain.VerificationCode;
import com.liveguard.exception.BusinessException;
import com.liveguard.repository.VerificationCodeRepository;
import com.liveguard.service.VerificationCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VerificationCodeServiceImp implements VerificationCodeService {

    private final VerificationCodeRepository verificationCodeRepository;

    public VerificationCodeServiceImp(VerificationCodeRepository verificationCodeRepository) {
        this.verificationCodeRepository = verificationCodeRepository;
    }

    @Override
    public VerificationCode save(VerificationCode verificationCode) {
        log.debug("VerificationCodeService | save | verificationCode: " + verificationCode.toString());

        try {
            VerificationCode savedCode = verificationCodeRepository.save(verificationCode);
            log.debug("VerificationCodeService | save | savedCode: " + savedCode.toString());
            return savedCode;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("VerificationCodeService | save | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
}
