package com.liveguard.util;

import com.liveguard.domain.EmailSettingBag;
import com.liveguard.domain.User;
import com.liveguard.exception.BusinessException;
import com.liveguard.service.SettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Slf4j
public class CustomerForgetPasswordUtil {

    public static void sendEmail(User user, SettingService settingService) {

        log.debug("CustomerForgetPasswordUtil | sendEmail | send reset link to user: " + user.getEmail());
        String link = settingService.getWebsiteLink() + "forgot-password/" + user.getResetPasswordToken();

        EmailSettingBag emailSettings = settingService.getEmailSettings();
        JavaMailSenderImpl mailSender = PrepareMailSenderUtil.prepareMailSender(emailSettings);

        log.debug("CustomerForgetPasswordUtil | sendEmail | mailSender : " + mailSender);

        String toAddress = user.getEmail();
        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

        try {
            helper.setFrom(emailSettings.getFromAddress(), emailSettings.getSenderName());
            helper.setTo(toAddress);
            helper.setSubject(subject);

            helper.setText(content, true);

            log.debug("CustomerForgetPasswordUtil | sendEmail | helper : " + helper.toString());
            log.debug("CustomerForgetPasswordUtil | sendEmail | message : " + message.toString());

            mailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("CustomerForgetPasswordUtil | sendEmail | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
