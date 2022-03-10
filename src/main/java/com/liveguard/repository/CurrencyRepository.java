package com.liveguard.repository;

import com.liveguard.domain.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    List<Currency> findAllByOrderByNameAsc();
}