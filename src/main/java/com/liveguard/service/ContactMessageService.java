package com.liveguard.service;

import com.liveguard.domain.ContactMessage;

import java.util.List;

public interface ContactMessageService {

    ContactMessage save(ContactMessage contactMessage);

    List<ContactMessage> findAll();

    List<ContactMessage> findAllReplied();

    List<ContactMessage> findAllNonReplied();
}
