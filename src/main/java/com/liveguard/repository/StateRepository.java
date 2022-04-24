package com.liveguard.repository;

import com.liveguard.domain.Country;
import com.liveguard.domain.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StateRepository extends JpaRepository<State, Long> {

    List<State> findByCountryIdOrderByNameAsc(Long id);

    State findByName(String name);

    Boolean existsByNameAndCountryId(String name, Long countryId);
}
