package com.liveguard.repository;

import com.liveguard.domain.ShippingRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShippingRateRepository extends PagingAndSortingRepository<ShippingRate, Long> {

    @Query("SELECT sr FROM ShippingRate sr WHERE sr.country LIKE %?1% OR sr.state LIKE %?1%")
    Page<ShippingRate> findAll(String keyword, Pageable pageable);

    @Query("UPDATE ShippingRate sr SET sr.codSupported = ?2 WHERE sr.id = ?1")
    @Modifying
    void updateCODSupport(Long id, boolean enabled);

    Page<ShippingRate> findAllByCodSupported(Boolean codSupported, Pageable pageable);

    Optional<ShippingRate> findByCountryAndState(String country, String state);

    Boolean existsByCountryIsIgnoreCaseAndStateIgnoreCase(String country, String state);
}
