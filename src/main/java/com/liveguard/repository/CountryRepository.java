package com.liveguard.repository;

import com.liveguard.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    List<Country> findAllByOrderByNameAsc();

    Country findByName(String name);

    Boolean existsByName(String name);
}
