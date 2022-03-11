package com.liveguard.repository;

import com.liveguard.domain.ChipType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class ChipTypeRepositoryTest {

    @Autowired
    ChipTypeRepository chipTypeRepository;

    @Test
    void findAll() {
        List<ChipType> chipTypes = chipTypeRepository.findAll();

        System.out.println(chipTypes);
        assertNotNull(chipTypes);
    }

    @Test
    void findAllByEnabledTrue() {
        List<ChipType> chipTypes = chipTypeRepository.findAllByEnabledTrue();

        System.out.println(chipTypes);
        assertNotNull(chipTypes);
    }

    @Test
    void updateEnabledStatus() {
    }

    @Test
    void updateInStockStatus() {
    }
}