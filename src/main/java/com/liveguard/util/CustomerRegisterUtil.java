package com.liveguard.util;

import com.liveguard.domain.*;
import com.liveguard.exception.BusinessException;
import com.liveguard.payload.CustomerRegisterRequest;
import com.liveguard.repository.RoleRepository;
import com.liveguard.repository.VerificationCodeRepository;
import com.liveguard.service.SettingService;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

@Slf4j
public class CustomerRegisterUtil {

    public static User convertCustomerRegisterRequestToUser(CustomerRegisterRequest customer,
                                                            PasswordEncoder passwordEncoder,
                                                            RoleRepository roleRepository) {

        log.debug("CustomerRegisterUtil | convertCustomerRegisterRequestToUser | user: " + customer.toString());

        User user = new User();

        user.setEmail(customer.getEmail());
        user.setName(customer.getName());
        user.setPassword(passwordEncoder.encode(customer.getPassword()));
        user.setPhone(customer.getPhone());
        user.setGender(customer.getGender());

        if (customer.getDateOfBirth() != null) {
            user.setDateOfBirth(DateConverterUtil.convertDateToLocalDate(customer.getDateOfBirth()));

        }
        user.setCreatedTime(LocalDateTime.now());
        user.setCredentialsNonExpired(true);
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setEnable(false);
        user.setAuthenticationType(AuthenticationType.DATABASE);

        try {
            user.getRoles().add(roleRepository.findById(2L).get());
        } catch (Exception e) {
            log.error("CustomerRegisterUtil | convertCustomerRegisterRequestToUser | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.debug("CustomerRegisterUtil | convertCustomerRegisterRequestToUser | user: " + user.toString());

        return user;
    }

    public static VerificationCode createVerificationCode(User user, VerificationCodeRepository verificationCodeRepository) {

        log.debug("CustomerRegisterUtil | createVerificationCode | user: " + user.getEmail());
        VerificationCode code = new VerificationCode();
        code.setCode(String.valueOf(RandomString.make(64)));
        code.setStatus(VerificationCodeStatus.ACTIVE);
        code.setTempVerify(0);
        code.setEmailSendStatus(EmailSendStatus.UNSEND);
        code.setUser(user);

        try {
            VerificationCode savedCode = verificationCodeRepository.save(code);
            log.debug("CustomerRegisterUtil | createVerificationCode | savedCode: " + savedCode.toString());
            return savedCode;
        } catch (Exception e) {
            log.error("CustomerRegisterUtil | createVerificationCode | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public static void sendVerificationCode(VerificationCode verificationCode,
                                            VerificationCodeRepository verificationCodeRepository,
                                            SettingService settingService) {
        log.debug("CustomerRegisterUtil | sendVerificationCode | verificationCode: " + verificationCode.getCode());

        String websiteLink = settingService.getWebsiteLink();

        EmailSettingBag emailSettings = settingService.getEmailSettings();
        JavaMailSenderImpl mailSender = PrepareMailSenderUtil.prepareMailSender(emailSettings);

        log.debug("CustomerRegisterUtil | sendVerificationCode | mailSender : " + mailSender);

        String subject = emailSettings.getCustomerVerifySubject();
        String content = emailSettings.getCustomerVerifyContent();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);

        try {
            messageHelper.setFrom(emailSettings.getFromAddress(), emailSettings.getSenderName());
            messageHelper.setTo(verificationCode.getUser().getEmail());
            messageHelper.setSubject(subject);

            String verifyURL = websiteLink + "verify/" + verificationCode.getCode();

            content = content.replace("[[name]]", verificationCode.getUser().getName());
            content = content.replace("[[url]]", verifyURL);

            messageHelper.setText(content, true);

            mailSender.send(message);

            log.debug("CustomerRegisterUtil | sendVerificationCode | email send");
            log.debug("CustomerRegisterUtil | sendVerificationCode | email send");

            verificationCodeRepository.updateVerificationCodeStatus(verificationCode.getId(), EmailSendStatus.SEND);

        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("CustomerRegisterUtil | sendVerificationCode | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
