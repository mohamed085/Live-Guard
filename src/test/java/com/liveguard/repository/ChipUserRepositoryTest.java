package com.liveguard.repository;

import com.liveguard.domain.ChipUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class ChipUserRepositoryTest {

    @Autowired
    ChipUserRepository chipUserRepository;

    @Test
    void findByChipIdAndUserId() {
        Long chipId = 1L;
        Long userId = 1L;

        ChipUser chipUser = chipUserRepository.findByChipIdAndUserId(chipId, userId).get();
        System.out.println(chipUser.toString());

        assertEquals(chipUser.getUser().getId(), userId);
    }

    @Test
    void existsByChipIdAndUserId() {
        Long chipId = 1L;
        Long userId = 1L;

        Boolean isExist = chipUserRepository.existsByChipIdAndUserId(chipId, userId);
        System.out.println(isExist);

        assertEquals(isExist, true);
    }
}