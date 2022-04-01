package com.liveguard.service.serviceImp;

import com.liveguard.domain.ContactMessage;
import com.liveguard.repository.ContactMessageRepository;
import com.liveguard.service.ContactMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ContactMessageServiceImp implements ContactMessageService {

    private final ContactMessageRepository contactMessageRepository;

    public ContactMessageServiceImp(ContactMessageRepository contactMessageRepository) {
        this.contactMessageRepository = contactMessageRepository;
    }

    @Override
    public ContactMessage save(ContactMessage contactMessage) {
        return null;
    }

    @Override
    public List<ContactMessage> findAll() {
        return null;
    }

    @Override
    public List<ContactMessage> findAllReplied() {
        return null;
    }

    @Override
    public List<ContactMessage> findAllNonReplied() {
        return null;
    }
}
