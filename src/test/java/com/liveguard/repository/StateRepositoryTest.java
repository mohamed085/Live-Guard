package com.liveguard.repository;

import com.liveguard.domain.Country;
import com.liveguard.domain.State;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class StateRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    StateRepository stateRepository;

    @Test
    void saveALl() {
        Long countryId = 1L;

        Country country = entityManager.find(Country.class, countryId);

        List<State> states = Arrays.asList(
                new State("Alexandria", country),
                new State("Aswan", country),
                new State("Asyut", country),
                new State("Beheira", country),
                new State("Beni Suef", country),
                new State("Cairo", country),
                new State("Dakahlia", country),
                new State("Damietta", country),
                new State("Faiyum", country),
                new State("Gharbia", country),
                new State("Giza", country),
                new State("Ismailia", country),
                new State("Kafr El Sheikh", country),
                new State("Luxor", country),
                new State("Matruh", country),
                new State("Minya", country),
                new State("Monufia", country),
                new State("New Valley", country),
                new State("North Sinai", country),
                new State("Port Said", country),
                new State("Qalyubia", country),
                new State("Qena", country),
                new State("Red Sea", country),
                new State("Sohag", country),
                new State("South Sinai", country),
                new State("South Sinai", country),
                new State("Suez", country)
        );

        stateRepository.saveAll(states);
    }

    @Test
    void findByCountryOrderByNameAsc() {
    }

    @Test
    void findByName() {
    }
}