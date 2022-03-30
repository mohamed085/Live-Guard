package com.liveguard.repository;

import com.liveguard.domain.Chip;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class ChipDetailRepositoryTest {

    @Autowired
    ChipDetailRepository chipDetailRepository;

    @Autowired
    ChipRepository chipRepository;

    @Test
    void deleteAll() {
        chipDetailRepository.deleteById(4L);
        chipDetailRepository.deleteById(5L);
        chipDetailRepository.deleteById(6L);

    }
}