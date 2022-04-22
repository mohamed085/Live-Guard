package com.liveguard.service;

import com.liveguard.domain.Email;

import java.util.Set;

public interface EmailService {

    void save(Email email);

    void updateStatusToSend(Long id);

    Set<Email> findNonSentEmails();
}
