package com.liveguard.service.serviceImp;

import com.liveguard.domain.Email;
import com.liveguard.domain.User;
import com.liveguard.exception.BusinessException;
import com.liveguard.payload.CustomerRegisterRequest;
import com.liveguard.service.*;
import com.liveguard.util.CustomerForgetPasswordUtil;
import com.liveguard.util.CustomerRegisterUtil;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
public class AuthServiceImp implements AuthService {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final SendEmailService sendEmailService;
    private final SettingService settingService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;


    public AuthServiceImp(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder,
                          EmailService emailService, SendEmailService sendEmailService,
                          SettingService settingService, AuthenticationManager authenticationManager,
                          TokenService tokenService) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.sendEmailService = sendEmailService;
        this.settingService = settingService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
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

            Email email = CustomerRegisterUtil.prepareVerificationEmail(user, settingService);

            emailService.save(email);
            sendEmailService.send(email);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("AuthService | register | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    @Transactional
    public void verify(String code) {
        log.debug("AuthService | verify | code: " + code);

        try {
            User user = userService.findByVerificationCode(code);

            log.debug("AuthService | verify | enable user id: " + user.getId());
            userService.updateVerificationCode(user.getId(), "");
            userService.updateEnableStatus(user.getId(), true);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("AuthService | verify | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public void forgotPassword(String email) {
        log.debug("AuthService | verify | email: " + email);

        try {
            User user = userService.findByEmail(email);

            String token = RandomString.make(30);
            user.setResetPasswordToken(token);
            userService.updateResetPasswordToken(user.getId(), token);

            Email email1 = CustomerForgetPasswordUtil.prepareForgetPasswordEmail(user, settingService);
            emailService.save(email1);
            sendEmailService.send(email1);

        } catch (Exception e) {
            log.error("AuthService | register | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public void resetPassword(String restPasswordToken, String newPassword) {
        log.debug("AuthService | resetPassword");

        try {
            User user = userService.findByResetPasswordToken(restPasswordToken);

            userService.updatePassword(user.getId(), passwordEncoder.encode(newPassword));
            userService.updateResetPasswordToken(user.getId(), "");
        } catch (Exception e) {
            log.error("AuthService | register | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String login(String email, String password) {
        log.debug("AuthService | Login | user try to login: " + email);

        try {
            userService.findByEmail(email);

            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            log.debug("AuthService | Login | user try to login: " + authenticate.getDetails());

            SecurityContextHolder.getContext().setAuthentication(authenticate);
            String token = tokenService.generateToken(authenticate);
            return token;
        } catch (Exception e) {
            log.error("AuthService | Login | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
