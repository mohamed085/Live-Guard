package com.liveguard.repository;

import com.liveguard.domain.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    public List<Currency> findAllByOrderByNameAsc();
}