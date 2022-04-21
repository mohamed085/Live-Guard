package com.liveguard.service.serviceImp;

import com.liveguard.domain.Email;
import com.liveguard.domain.EmailSettingBag;
import com.liveguard.service.EmailService;
import com.liveguard.service.SendEmailService;
import com.liveguard.service.SettingService;
import com.liveguard.util.PrepareMailSenderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

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

        log.debug("EmailService | send | email: " + email.toString());
        log.debug("EmailService | send | emailSetting: " + emailSetting.getSenderName());

        JavaMailSenderImpl mailSender = PrepareMailSenderUtil.prepareMailSender(emailSetting);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);

        messageHelper.setFrom(emailSetting.getFromAddress(), emailSetting.getSenderName());
        messageHelper.setTo(email.getReceiver());
        messageHelper.setSubject(email.getSubject());
        messageHelper.setText(email.getContent(), true);

        mailSender.send(message);

        emailService.updateStatusToSend(email.getId());
        log.debug("EmailService | Send verification email | email send");
    }
}
