package com.liveguard.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class ShippingRateRepositoryTest {

    @Autowired
    ShippingRateRepository shippingRateRepository;

    @Test
    void findAll() {
    }

    @Test
    void updateCODSupport() {
    }

    @Test
    void findAllByCodSupported() {
    }

    @Test
    void findByCountryAndState() {
    }

    @Test
    void existsByCountryIsIgnoreCaseAndStateIgnoreCase() {
        Boolean status = shippingRateRepository.existsByCountryIsIgnoreCaseAndStateIgnoreCase("egypt", "qalyubia");

        System.out.println(status);
    }
}