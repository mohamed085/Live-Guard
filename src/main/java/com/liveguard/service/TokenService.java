package com.liveguard.service;

import org.springframework.security.core.Authentication;

public interface TokenService {

    String generateToken(Authentication authResult);
}
