package com.liveguard.service.serviceImp;

import com.auth0.jwt.JWT;
import com.liveguard.domain.LiveGuardUserDetails;
import com.liveguard.service.TokenService;
import com.liveguard.util.JwtProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Slf4j
@Service
public class TokenServiceImp implements TokenService {

    @Override
    public String generateToken(Authentication authResult) {
        LiveGuardUserDetails userDetails = (LiveGuardUserDetails) authResult.getPrincipal();
        log.debug("GenerateTokenUtil | Generate token to user: " + userDetails.getUsername());

        // Create JWT Token
        String token = JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .sign(HMAC512(JwtProperties.SECRET.getBytes()));

        return token;
    }
}
