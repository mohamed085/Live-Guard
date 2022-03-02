package com.liveguard.service.serviceImp;

import com.liveguard.domain.EmailSendStatus;
import com.liveguard.domain.User;
import com.liveguard.domain.VerificationCode;
import com.liveguard.domain.VerificationCodeStatus;
import com.liveguard.exception.BusinessException;
import com.liveguard.repository.UserRepository;
import com.liveguard.repository.VerificationCodeRepository;
import com.liveguard.service.VerificationCodeService;
import com.liveguard.util.GenerateCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
public class VerificationCodeServiceImp implements VerificationCodeService {

    private final VerificationCodeRepository verificationCodeRepository;
    private final UserRepository userRepository;

    public VerificationCodeServiceImp(VerificationCodeRepository verificationCodeRepository, UserRepository userRepository) {
        this.verificationCodeRepository = verificationCodeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public VerificationCode generateCode(User user) {
        log.debug("VerificationCodeService | generateCode | user: " + user.getEmail());
        VerificationCode code = new VerificationCode();
        code.setCode(String.valueOf(GenerateCodeUtil.generateRandomDigits(6)));
        code.setStatus(VerificationCodeStatus.ACTIVE);
        code.setTempVerify(0);
        code.setEmailSendStatus(EmailSendStatus.UNSEND);
        code.setUser(user);

        return code;
    }

    @Override
    public VerificationCode save(VerificationCode code) {
        log.debug("VerificationCodeService | save | code id: " + code.getId());
        try {
            return verificationCodeRepository.save(code);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public VerificationCode findByUserId(Long id) {
        log.debug("VerificationCodeService | findByUserId | code id: " + id);
        try {
            return verificationCodeRepository.findByUserId(id);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void updateEmailSendStatus(Long userId, EmailSendStatus status) {
        log.debug("VerificationCodeService | updateEmailSendStatus | userId: " + userId + " ,status: " + status);

        VerificationCode code = findByUserId(userId);
        code.setEmailSendStatus(status);
        try {
            verificationCodeRepository.save(code);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Set<VerificationCode> findUnsendedEmail() {
        log.debug("VerificationCodeService | findUnsendedEmail");

        try {
            return verificationCodeRepository.findByEmailSendStatus(EmailSendStatus.UNSEND);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
