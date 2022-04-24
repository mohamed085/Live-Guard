package com.liveguard.repository;

import com.liveguard.domain.Country;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
class CountryRepositoryTest {

    @Autowired
    CountryRepository countryRepository;

    @Test
    void save() {
        Country country = countryRepository.save(new Country("Egypt", "EG"));

        System.out.println(country);
        assertEquals(country.getName(), "Egypt");
    }

    @Test
    void findAllByOrderByNameAsc() {
    }

    @Test
    void findByName() {
    }
}