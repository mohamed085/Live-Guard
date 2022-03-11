package com.liveguard.service;

import com.liveguard.domain.Country;

import java.util.List;

public interface CountryService {

    List<Country> findAllByOrderByNameAsc();

    Country findByName(String name);

    Boolean isExist(String name);

    void add(Country country);

    void update(Country country);

    void deleteById(Long id);
}
