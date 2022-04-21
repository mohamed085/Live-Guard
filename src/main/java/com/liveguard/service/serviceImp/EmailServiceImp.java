package com.liveguard.service.serviceImp;

import com.liveguard.domain.Email;
import com.liveguard.domain.EmailSendStatus;
import com.liveguard.exception.BusinessException;
import com.liveguard.repository.EmailRepository;
import com.liveguard.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Slf4j
@Service
public class EmailServiceImp implements EmailService {

    private final EmailRepository emailRepository;

    public EmailServiceImp(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    @Override
    public void save(Email email) {
        log.debug("EmailService | save | user user: " + email.toString());
        try {
            email.setCreatedDate(LocalDateTime.now());
            emailRepository.save(email);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("EmailService | save | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public void updateStatusToSend(Long id) {
        log.debug("EmailService | updateStatusToSend | user id: " + id);

        try {
            emailRepository.updateStatus(id, EmailSendStatus.SEND, LocalDateTime.now());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("EmailService | save | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
