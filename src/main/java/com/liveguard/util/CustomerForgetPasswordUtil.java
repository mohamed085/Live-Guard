package com.liveguard.util;

import com.liveguard.domain.Email;
import com.liveguard.domain.EmailSendStatus;
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

    public static Email prepareForgetPasswordEmail(User user, SettingService settingService) {
        log.debug("CustomerForgetPasswordUtil | prepareForgetPasswordEmail | user: " + user.toString());

        String link = settingService.getWebsiteLink() + "reset-password/" + user.getResetPasswordToken();
        log.debug("CustomerForgetPasswordUtil | prepareForgetPasswordEmail | link: " + link);


        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";


        log.debug("CustomerForgetPasswordUtil | prepareForgetPasswordEmail | subject: " + subject);
        log.debug("CustomerForgetPasswordUtil | prepareForgetPasswordEmail | content: " + content);


        Email email = new Email();

        email.setReceiver(user.getEmail());
        email.setSubject(subject);
        email.setContent(content);
        email.setStatus(EmailSendStatus.UNSEND);

        log.debug("CustomerForgetPasswordUtil | prepareForgetPasswordEmail | email: " + email.toString());

        return email;
    }
}
