package com.liveguard.repository;

import com.liveguard.domain.Country;
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
class CountryRepositoryTest {

    @Autowired
    private CountryRepository repo;

    @Test
    public void saveCountry() {
        Country country = repo.save(new Country("Egypt", "EGP"));

        assertNotNull(country);
    }

    @Test
    public void findAllByOrderByNameAsc() {
        List<Country> listCountries = repo.findAllByOrderByNameAsc();
        listCountries.forEach(System.out::println);

        assertNotNull(listCountries);
    }

}