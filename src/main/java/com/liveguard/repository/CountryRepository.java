package com.liveguard.repository;

import com.liveguard.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    public List<Country> findAllByOrderByNameAsc();

    Boolean existsByName(String name);

    @Query("SELECT c FROM Country c WHERE c.name = :name")
    public Country findByName(String name);

}
