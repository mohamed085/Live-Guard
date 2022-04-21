package com.liveguard.service;

import com.liveguard.domain.Email;

public interface EmailService {

    void save(Email email);

    void updateStatusToSend(Long id);
}
