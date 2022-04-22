package com.liveguard.util;

import com.liveguard.domain.*;
import com.liveguard.payload.CustomerRegisterRequest;
import com.liveguard.service.RoleService;
import com.liveguard.service.SettingService;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Slf4j
public class CustomerRegisterUtil {

    public static User convertCustomerRegisterRequestToUser(CustomerRegisterRequest customer,
                                                            PasswordEncoder passwordEncoder,
                                                            RoleService roleService) {

        log.debug("CustomerRegisterUtil | convertCustomerRegisterRequestToUser | user: " + customer.toString());

        User user = new User();

        user.setEmail(customer.getEmail());
        user.setName(customer.getName());
        user.setPassword(passwordEncoder.encode(customer.getPassword()));
        user.setPhone(customer.getPhone());
        user.setGender(customer.getGender());
        user.setVerificationCode(String.valueOf(RandomString.make(64)));

        if (customer.getDateOfBirth() != null) {
            user.setDateOfBirth(DateConverterUtil.convertDateToLocalDate(customer.getDateOfBirth()));

        }
        user.setCreatedTime(LocalDateTime.now());
        user.setCredentialsNonExpired(true);
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setEnable(false);
        user.setAuthenticationType(AuthenticationType.DATABASE);
        user.getRoles().add(roleService.findByRole("Customer"));

        log.debug("CustomerRegisterUtil | convertCustomerRegisterRequestToUser | user: " + user.toString());

        return user;
    }


    public static Email prepareVerificationEmail(User user, SettingService settingService) {
        log.debug("CustomerRegisterUtil | prepareVerificationEmail | verificationCode: " + user.getVerificationCode());

        String websiteLink = settingService.getWebsiteLink();
        log.debug("CustomerRegisterUtil | prepareVerificationEmail | websiteLink: " + websiteLink);

        String verifyURL = websiteLink + "verify/" + user.getVerificationCode();
        log.debug("CustomerRegisterUtil | prepareVerificationEmail | verifyURL: " + verifyURL);

        EmailSettingBag emailSettings = settingService.getEmailSettings();

        String subject = emailSettings.getCustomerVerifySubject();
        String content = emailSettings.getCustomerVerifyContent();

        content = content.replace("[[name]]", user.getName());
        content = content.replace("[[url]]", verifyURL);

        log.debug("CustomerRegisterUtil | prepareVerificationEmail | subject: " + subject);
        log.debug("CustomerRegisterUtil | prepareVerificationEmail | content: " + content);

        Email email = new Email();

        email.setReceiver(user.getEmail());
        email.setSubject(subject);
        email.setContent(content);
        email.setStatus(EmailSendStatus.UNSEND);

        log.debug("CustomerRegisterUtil | prepareVerificationEmail | email: " + email.toString());

        return email;
    }

}
