package com.liveguard.service.serviceImp;

import com.liveguard.domain.Email;
import com.liveguard.domain.User;
import com.liveguard.domain.VerificationCode;
import com.liveguard.exception.BusinessException;
import com.liveguard.payload.CustomerRegisterRequest;
import com.liveguard.service.*;
import com.liveguard.util.CustomerRegisterUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthServiceImp implements AuthService {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final VerificationCodeService verificationCodeService;
    private final EmailService emailService;
    private final SendEmailService sendEmailService;
    private final SettingService settingService;


    public AuthServiceImp(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder, VerificationCodeService verificationCodeService, EmailService emailService, SendEmailService sendEmailService, SettingService settingService) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.verificationCodeService = verificationCodeService;
        this.emailService = emailService;
        this.sendEmailService = sendEmailService;
        this.settingService = settingService;
    }

    @Override
    public void register(CustomerRegisterRequest customer) {
        log.debug("AuthService | register | user: " + customer.toString());

        if (userService.existsByEmail(customer.getEmail())) {
            log.error("AuthService | register | error: User already exist");
            throw new BusinessException("User already exist", HttpStatus.BAD_REQUEST);
        }

        try {
            User user = CustomerRegisterUtil.convertCustomerRegisterRequestToUser
                    (customer, passwordEncoder, roleService);

            userService.save(user);
            VerificationCode verificationCode = CustomerRegisterUtil
                    .createVerificationCode(user, verificationCodeService);

            Email email = CustomerRegisterUtil.prepareVerificationEmail(verificationCode, settingService);

            emailService.save(email);
            sendEmailService.send(email);

        } catch (Exception e) {
            log.error("AuthService | register | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public void verify(String code) {

    }

    @Override
    public void forgotPassword(String email) {

    }

    @Override
    public void resetPassword(String restPasswordToken, String newPassword) {

    }

    @Override
    public String login(String email, String password) {
        return null;
    }
}
