package com.liveguard.service.serviceImp;

import com.liveguard.domain.Country;
import com.liveguard.exception.BusinessException;
import com.liveguard.repository.CountryRepository;
import com.liveguard.service.CountryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CountryServiceImp implements CountryService {

    private final CountryRepository countryRepository;

    public CountryServiceImp(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public List<Country> findAllByOrderByNameAsc() {
        log.debug("CountryService | findAllByOrderByNameAsc");

        try {
            return countryRepository.findAllByOrderByNameAsc();
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Country findByName(String name) {
        log.debug("CountryService | findByName | name: " + name);

        try {
            return countryRepository.findByName(name);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Country findById(Long id) {
        log.debug("CountryService | findById | id: " + id);

        try {
            return countryRepository.findById(id)
                    .orElseThrow(() -> new BusinessException("Country not found", HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Boolean isExist(String name) {
        log.debug("CountryService | isExist | name: " + name);

        try {
            return countryRepository.existsByName(name);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void add(Country country) {
        log.debug("CountryService | isExist | add: " + country.getName());
        if (isExist(country.getName())) {
            log.error("CountryService | isExist | Country already exist: " + country.getName());
            throw new BusinessException("Country already exist", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            countryRepository.save(country);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public void update(Long id, Country country) {
        country.setId(id);

        log.debug("CountryService | update | add: " + country.getName());
        if (isExist(country.getName())) {
            log.error("CountryService | update | Country already exist: " + country.getName());
            throw new BusinessException("Country already exist", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            countryRepository.save(country);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void deleteById(Long id) {
        log.debug("CountryService | deleteById | id: " + id);
        try {
            countryRepository.deleteById(id);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
