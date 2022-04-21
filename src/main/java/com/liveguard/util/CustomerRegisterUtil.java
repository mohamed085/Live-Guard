package com.liveguard.util;

import com.liveguard.domain.*;
import com.liveguard.exception.BusinessException;
import com.liveguard.payload.CustomerRegisterRequest;
import com.liveguard.service.RoleService;
import com.liveguard.service.SettingService;
import com.liveguard.service.VerificationCodeService;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.http.HttpStatus;
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

    public static VerificationCode createVerificationCode(User user,
                                                          VerificationCodeService verificationCodeService) {

        log.debug("CustomerRegisterUtil | createVerificationCode | user: " + user.getEmail());
        VerificationCode code = new VerificationCode();
        code.setCode(String.valueOf(RandomString.make(64)));
        code.setStatus(VerificationCodeStatus.ACTIVE);
        code.setTempVerify(0);
        code.setUser(user);
        VerificationCode savedCode = verificationCodeService.save(code);
        log.debug("CustomerRegisterUtil | createVerificationCode | savedCode: " + savedCode.toString());
        return savedCode;

    }


    public static Email prepareVerificationEmail(VerificationCode verificationCode, SettingService settingService) {
        log.debug("CustomerRegisterUtil | prepareVerificationEmail | verificationCode: " + verificationCode.getCode());

        String websiteLink = settingService.getWebsiteLink();
        log.debug("CustomerRegisterUtil | prepareVerificationEmail | websiteLink: " + websiteLink);

        String verifyURL = websiteLink + "verify/" + verificationCode.getCode();
        log.debug("CustomerRegisterUtil | prepareVerificationEmail | verifyURL: " + verifyURL);

        EmailSettingBag emailSettings = settingService.getEmailSettings();

        String subject = emailSettings.getCustomerVerifySubject();
        String content = emailSettings.getCustomerVerifyContent();

        content = content.replace("[[name]]", verificationCode.getUser().getName());
        content = content.replace("[[url]]", verifyURL);

        log.debug("CustomerRegisterUtil | prepareVerificationEmail | subject: " + subject);
        log.debug("CustomerRegisterUtil | prepareVerificationEmail | content: " + content);


        Email email = new Email();

        email.setReceiver(verificationCode.getUser().getEmail());
        email.setSubject(subject);
        email.setContent(content);
        email.setStatus(EmailSendStatus.UNSEND);

        return email;
    }

}
