package com.liveguard.service;

import com.liveguard.domain.Country;

import java.util.List;

public interface CountryService {

    List<Country> findAllByOrderByNameAsc();

    Country findByName(String name);

    Country findById(Long id);

    Boolean isExist(String name);

    void add(Country country);

    void update(Long id, Country country);

    void deleteById(Long id);
}