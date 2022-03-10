package com.liveguard.service.serviceImp;

import com.liveguard.domain.Currency;
import com.liveguard.exception.BusinessException;
import com.liveguard.repository.CurrencyRepository;
import com.liveguard.service.CurrencyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
        try {
            return currencyRepository.findAllByOrderByNameAsc();
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
