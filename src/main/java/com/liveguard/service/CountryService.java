package com.liveguard.service;

import com.liveguard.domain.Country;

import java.util.List;

public interface CountryService {

    Country findById(Long id);
    List<Country> findAll();

    void save(Country country);

    void delete(Long id);

    Boolean isExists(String name);

    void update(Country country);
}
