package com.liveguard.util;

import com.liveguard.domain.EmailSettingBag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Slf4j
public class PrepareMailSenderUtil {

    public static JavaMailSenderImpl prepareMailSender(EmailSettingBag settings) {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(settings.getHost());
        mailSender.setPort(settings.getPort());
        mailSender.setUsername(settings.getUsername());
        mailSender.setPassword(settings.getPassword());
        mailSender.setDefaultEncoding("utf-8");

        Properties mailProperties = new Properties();
        mailProperties.setProperty("mail.smtp.auth", settings.getSmtpAuth());
        mailProperties.setProperty("mail.smtp.starttls.enable", settings.getSmtpSecured());
        mailProperties.setProperty("mail.smtp.ssl.trust", "true");
        mailProperties.setProperty("mail.smtp.ssl.trust", settings.getHost());

        mailSender.setJavaMailProperties(mailProperties);

        log.debug("PrepareMailSenderUtil | prepareMailSender | mailSender : " + mailSender.toString());

        return mailSender;
    }

}
