package com.liveguard.service.serviceImp;

import com.liveguard.domain.EmailSendStatus;
import com.liveguard.domain.EmailSettingBag;
import com.liveguard.domain.VerificationCode;
import com.liveguard.service.EmailService;
import com.liveguard.service.SettingService;
import com.liveguard.service.VerificationCodeService;
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
public class EmailServiceImp implements EmailService {

    private final SettingService settingService;
    private final VerificationCodeService verificationCodeService;

    public EmailServiceImp(SettingService settingService, VerificationCodeService verificationCodeService) {
        this.settingService = settingService;
        this.verificationCodeService = verificationCodeService;
    }

    @Async
    @Override
    public void sendVerificationEmail(VerificationCode code) throws UnsupportedEncodingException, MessagingException {
        log.debug("EmailService | sendVerificationEmail | start");

        EmailSettingBag emailSetting = settingService.getEmailSettings();

        log.debug("EmailService | sendVerificationEmail | emailSetting: " + emailSetting.getSenderName());
        JavaMailSenderImpl mailSender = PrepareMailSenderUtil.prepareMailSender(emailSetting);

        String subject = emailSetting.getCustomerVerifySubject();
        String content = emailSetting.getCustomerVerifyContent();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);

        messageHelper.setFrom(emailSetting.getFromAddress(), emailSetting.getSenderName());
        messageHelper.setTo(code.getUser().getEmail());
        messageHelper.setSubject(subject);

        content = content.replace("[[name]]", code.getUser().getName());
        content = content.replace("[[Verify Code]]", code.getCode());

        messageHelper.setText(content);

        mailSender.send(message);

        log.debug("EmailService | sendVerificationEmail | email send");

        verificationCodeService.updateEmailSendStatus(code.getUser().getId(), EmailSendStatus.SEND);
    }

    @Async
    @Override
    public void sendResetTokenPassword(String email, String passwordToken) throws UnsupportedEncodingException, MessagingException {
        log.debug("EmailService | sendResetTokenPassword | start");

        EmailSettingBag emailSetting = settingService.getEmailSettings();

        log.debug("EmailService | sendVerificationEmail | emailSetting: " + emailSetting.getSenderName());
        JavaMailSenderImpl mailSender = PrepareMailSenderUtil.prepareMailSender(emailSetting);

        String subject = emailSetting.getCustomerVerifySubject();
        String content = emailSetting.getCustomerVerifyContent();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);

        messageHelper.setFrom(emailSetting.getFromAddress(), emailSetting.getSenderName());
        messageHelper.setTo(email);
        messageHelper.setSubject(subject);

        content = content.replace("[[name]]", email);
        content = content.replace("[[Verify Code]]", passwordToken);

        messageHelper.setText(content);

        mailSender.send(message);

        log.debug("EmailService | sendVerificationEmail | email send");
    }

    @Scheduled(fixedRate = 7200000L)
    @Override
    public void sendVerificationEmailToUnsendedJob() throws UnsupportedEncodingException, MessagingException {
        log.debug("EmailService | sendVerificationEmailToUnsendedJob | start");
        Set<VerificationCode> codes = verificationCodeService.findUnsendedEmail();
        if (codes.isEmpty()) return;

        for (VerificationCode code: codes) {
            log.debug("EmailService | sendVerificationEmailToUnsendedJob | send verify code to: " + code.getUser().getEmail());
            sendVerificationEmail(code);
            log.debug("EmailService | sendVerificationEmailToUnsendedJob  | Send verification email | email send");

        }
    }
}
