package com.liveguard.service.serviceImp;

import com.liveguard.domain.Email;
import com.liveguard.domain.EmailSendStatus;
import com.liveguard.domain.EmailSettingBag;
import com.liveguard.service.EmailService;
import com.liveguard.service.SendEmailService;
import com.liveguard.service.SettingService;
import com.liveguard.util.PrepareMailSenderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Set;

@Slf4j
@Service
public class SendEmailServiceImp implements SendEmailService {

    private final EmailService emailService;
    private final SettingService settingService;

    public SendEmailServiceImp(EmailService emailService, SettingService settingService) {
        this.emailService = emailService;
        this.settingService = settingService;
    }

    @Override
    @Async
    public void send(Email email) throws MessagingException, UnsupportedEncodingException {
        EmailSettingBag emailSetting = settingService.getEmailSettings();

        log.debug("SendEmailService | send | email: " + email.toString());
        log.debug("SendEmailService | send | emailSetting: " + emailSetting.getSenderName());

        JavaMailSenderImpl mailSender = PrepareMailSenderUtil.prepareMailSender(emailSetting);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);

        messageHelper.setFrom(emailSetting.getFromAddress(), emailSetting.getSenderName());
        messageHelper.setTo(email.getReceiver());
        messageHelper.setSubject(email.getSubject());
        messageHelper.setText(email.getContent(), true);

        mailSender.send(message);

        emailService.updateStatusToSend(email.getId());
        log.debug("SendEmailService | Send verification email | email send");
    }

    @Override
    @Scheduled(fixedRate = 7200000L)
    public void sendEmailToNonSentEmailsJob() {
        log.debug("SendEmailService | sendEmailToNonSentEmailsJob created");
        Set<Email> emails = emailService.findNonSentEmails();

        if (emails.isEmpty()) return;

        emails.forEach(email -> {
            log.debug("SendEmailService | sendEmailToNonSentEmailsJob | email: " + email);
            try {
                send(email);
            } catch (MessagingException | UnsupportedEncodingException e) {
                e.printStackTrace();
                log.error("SendEmailService | sendEmailToNonSentEmailsJob | error: " + e.getMessage());

            }
            log.debug("SendEmailService | sendEmailToNonSentEmailsJob | email: " + email);
        });

    }

}
