package com.liveguard.repository;

import com.liveguard.domain.Email;
import com.liveguard.domain.EmailSendStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class EmailRepositoryTest {

    @Autowired
    EmailRepository emailRepository;

    @Test
    void updateStatus() {
    }

    @Test
    void findAllByStatus() {
        Set<Email> emails = emailRepository.findAllByStatus(EmailSendStatus.UNSEND);

        System.out.println(emails);
    }
}