package com.liveguard.repository;

import com.liveguard.domain.Country;
import com.liveguard.domain.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

    List<State> findByCountryIdOrderByNameAsc(Long countryId);

    Boolean existsByName(String name);

    State findByName(String name);

}
