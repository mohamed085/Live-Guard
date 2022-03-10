package com.liveguard.service.serviceImp;

import com.liveguard.domain.Currency;
import com.liveguard.repository.CurrencyRepository;
import com.liveguard.service.CurrencyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CurrencyServiceImp implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    public CurrencyServiceImp(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public List<Currency> findAll() {
        log.debug("CurrencyService | findAll");
        return currencyRepository.findAllByOrderByNameAsc();
    }
}
