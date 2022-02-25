package com.liveguard.service.serviceImp;

import com.liveguard.domain.EmailSendStatus;
import com.liveguard.domain.User;
import com.liveguard.domain.VerificationCode;
import com.liveguard.domain.VerificationCodeStatus;
import com.liveguard.repository.UserRepository;
import com.liveguard.repository.VerificationCodeRepository;
import com.liveguard.service.VerificationCodeService;
import com.liveguard.util.GenerateCodeUtil;
import lombok.extern.slf4j.Slf4j;
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
        return verificationCodeRepository.save(code);
    }

    @Override
    public VerificationCode findByUserId(Long id) {
        log.debug("VerificationCodeService | findByUserId | code id: " + id);
        return verificationCodeRepository.findByUserId(id);
    }

    @Override
    public void updateEmailSendStatus(Long userId, EmailSendStatus status) {
        log.debug("VerificationCodeService | updateEmailSendStatus | userId: " + userId + " ,status: " + status);

        VerificationCode code = findByUserId(userId);
        code.setEmailSendStatus(status);
        verificationCodeRepository.save(code);
    }

    @Override
    public Set<VerificationCode> findUnsendedEmail() {
        log.debug("VerificationCodeService | findUnsendedEmail");

        return verificationCodeRepository.findByEmailSendStatus(EmailSendStatus.UNSEND);
    }


}
