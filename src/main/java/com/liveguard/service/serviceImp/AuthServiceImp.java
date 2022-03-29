package com.liveguard.service.serviceImp;

import com.liveguard.domain.User;
import com.liveguard.domain.VerificationCode;
import com.liveguard.exception.BusinessException;
import com.liveguard.payload.CustomerRegisterRequest;
import com.liveguard.repository.RoleRepository;
import com.liveguard.repository.UserRepository;
import com.liveguard.repository.VerificationCodeRepository;
import com.liveguard.service.AuthService;
import com.liveguard.service.SettingService;
import com.liveguard.service.TokenService;
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

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationCodeRepository verificationCodeRepository;
    private final SettingService settingService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthServiceImp(UserRepository userRepository, RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder, SettingService settingService,
                          VerificationCodeRepository verificationCodeRepository,
                          AuthenticationManager authenticationManager, TokenService tokenService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.settingService = settingService;
        this.verificationCodeRepository = verificationCodeRepository;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @Override
    @Transactional
    public void register(CustomerRegisterRequest customer) {
        log.debug("AuthService | register | user: " + customer.toString());

        if (userRepository.existsByEmail(customer.getEmail())) {
            log.error("AuthService | register | error: User already exist");
            throw new BusinessException("User already exist", HttpStatus.BAD_REQUEST);
        }

        User user = CustomerRegisterUtil.convertCustomerRegisterRequestToUser(customer, passwordEncoder, roleRepository);

        try {
            userRepository.save(user);
            VerificationCode verificationCode = CustomerRegisterUtil.createVerificationCode(user, verificationCodeRepository);

            CustomerRegisterUtil.sendVerificationCode(verificationCode, verificationCodeRepository, settingService);
            log.debug("AuthService | register | user saved");

        } catch (Exception e) {
            log.error("AuthService | register | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    @Transactional
    public void verify(String code) {
        log.debug("AuthService | verify | code: " + code);

        try {
            VerificationCode verificationCode = verificationCodeRepository.findByCode(code)
                    .orElseThrow(() -> new BusinessException("User not found", HttpStatus.BAD_REQUEST));

            User user = verificationCode.getUser();

            log.debug("AuthService | verify | enable user id: " + user.getId());
            userRepository.updateEnableStatus(user.getId(), true);
        } catch (Exception e) {
            log.error("AuthService | register | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public void forgotPassword(String email) {
        log.debug("AuthService | verify | email: " + email);

        try {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new BusinessException("User not found", HttpStatus.BAD_REQUEST));

            String token = RandomString.make(30);
            user.setResetPasswordToken(token);
            userRepository.updateResetPasswordToken(user.getId(), token);

            CustomerForgetPasswordUtil.sendEmail(user, settingService);

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
            User user = userRepository.findByResetPasswordToken(restPasswordToken)
                    .orElseThrow(() -> new BusinessException("User not found", HttpStatus.BAD_REQUEST));

            userRepository.updatePassword(user.getId(), passwordEncoder.encode(newPassword));
            userRepository.updateResetPasswordToken(user.getId(), "");
        } catch (Exception e) {
            log.error("AuthService | register | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String login(String email, String password) {
        log.debug("AuthService | Login | user try to login: " + email);

        try {
            userRepository.findByEmail(email)
                    .orElseThrow(() -> new BusinessException("User not found", HttpStatus.BAD_REQUEST));

            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            log.debug("AuthService | Login | user try to login: " + authenticate.getDetails());

            SecurityContextHolder.getContext().setAuthentication(authenticate);
            String token = tokenService.generateToken(authenticate);
            return token;
        } catch (Exception e) {
            log.error("AuthService | register | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
