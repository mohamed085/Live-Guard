package com.liveguard.service.serviceImp;

import com.liveguard.domain.AuthenticationType;
import com.liveguard.domain.User;
import com.liveguard.domain.VerificationCode;
import com.liveguard.dto.UserDTO;
import com.liveguard.exception.BusinessException;
import com.liveguard.mapper.UserMapper;
import com.liveguard.payload.VerifyAccountRequest;
import com.liveguard.payload.VerifyAccountResponse;
import com.liveguard.service.*;
import com.liveguard.util.GenerateCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@Service
public class AuthServiceImp implements AuthService {

    private final UserService userService;
    private final RoleService roleService;
    private final VerificationCodeService verificationCodeService;
    private final EmailService emailService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImp(UserService userService, RoleService roleService, VerificationCodeService verificationCodeService, EmailService emailService, TokenService tokenService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.verificationCodeService = verificationCodeService;
        this.emailService = emailService;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(UserDTO userDTO) {
        log.debug("AuthService | register | user: " + userDTO.getEmail());

        User user = UserMapper.UserDTOToUser(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(roleService.findById(1L));
        user.setCreatedTime(LocalDateTime.now());
        user.setCredentialsNonExpired(true);
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setEnable(false);
        user.setAuthenticationType(AuthenticationType.DATABASE);

        userService.save(user);

        VerificationCode code = verificationCodeService.generateCode(user);
        verificationCodeService.save(code);
        try {
            emailService.sendVerificationEmail(verificationCodeService.findByUserId(user.getId()));
        } catch (UnsupportedEncodingException e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (MessagingException e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public String login(String email, String password) {
        log.debug("AuthService | Login | user try to login: " + email);

        userService.findByEmail(email);
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        log.debug("AuthService | Login | user try to login: " + authenticate.getDetails());

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = tokenService.generateToken(authenticate);

        return token;
    }

    @Override
    public VerifyAccountResponse verifyAccount(VerifyAccountRequest request) {
        log.debug("AuthService | VerifyAccount | verify: " + request.getUserEmail());

        User user = userService.findByEmail(request.getUserEmail());
        VerificationCode code = verificationCodeService.findByUserId(user.getId());

        if (code.getTempVerify() < 5) {
            return CheckVerifyCode(request, code);
        }
        else {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime createdDate = code.getCreateDate();

            log.debug("AuthService | VerifyAccount | date now: " + now);
            log.debug("AuthService | VerifyAccount | created date now: " + createdDate);

            Duration duration = Duration.between(now, createdDate);
            long diff = Math.abs(duration.toMinutes());

            log.debug("AuthService | VerifyAccount | diff date: " + diff);

            if (diff > 60) {
                code.getUser().setAccountNonLocked(true);
                code.setTempVerify(0);
                return CheckVerifyCode(request, code);
            }
            else {
                return new VerifyAccountResponse("You are blocked now", code.getUser().getEmail());
            }
        }
    }

    @Override
    public void resendVerifyAccount(String email) {
        log.debug("AuthService | resendVerifyAccount | user: " + email);

        User user = userService.findByEmail(email);
        try {
            emailService.sendVerificationEmail(verificationCodeService.findByUserId(user.getId()));
        } catch (UnsupportedEncodingException | MessagingException e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void sendResetPasswordToken(String email) {
        log.debug("AuthService | resetPassword | user: " + email);

        User user = userService.findByEmail(email);
        String resetPasswordToken = String.valueOf(GenerateCodeUtil.generateRandomDigits(10));
        user.setResetPasswordToken(resetPasswordToken);
        userService.save(user);

        try {
            emailService.sendResetTokenPassword(user.getEmail(), user.getResetPasswordToken());
        } catch (UnsupportedEncodingException | MessagingException e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void resetPassword(String userEmail, String resetPasswordToken, String newPassword) {
        log.debug("AuthService | resetPassword | user: " + userEmail);

        User user = userService.findByEmail(userEmail);
        if (user.getResetPasswordToken().equals(resetPasswordToken)) {
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setResetPasswordToken(null);
            userService.save(user);
        } else {
            throw new BusinessException("User reset password code incorrect", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private VerifyAccountResponse CheckVerifyCode(VerifyAccountRequest request, VerificationCode code) {
        code.setCreateDate(LocalDateTime.now());

        if (code.getCode().equals(request.getUserCode())) {
            log.debug("AuthService | VerifyAccount | account verified");

            code.setTempVerify(0);
            code.getUser().setEnable(true);
            verificationCodeService.save(code);
            return new VerifyAccountResponse("Account verified successfully", code.getUser().getEmail());
        }
        else {
            log.debug("AuthService | VerifyAccount | account not verified");

            code.setTempVerify(code.getTempVerify() + 1);
            if (code.getTempVerify() == 5) {
                log.debug("UserService | VerifyAccount | account blocked");

                code.getUser().setAccountNonLocked(false);
                verificationCodeService.save(code);
                return new VerifyAccountResponse("Your code is incorrect, and your account blocked for 1 hour", code.getUser().getEmail());
            }
            verificationCodeService.save(code);
            return new VerifyAccountResponse("Your code is incorrect", code.getUser().getEmail());
        }
    }

}
