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
    public Country findById(Long id) {
        log.debug("CountryService | findById | id: " + id);

        try {
            return countryRepository.findById(id)
                    .orElseThrow(() -> new BusinessException("Country not found", HttpStatus.NOT_FOUND));

        } catch (Exception e) {
            e.printStackTrace();
            log.error("CountryService | findAll | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Country> findAll() {
        log.debug("CountryService | findAll");

        try {
            return countryRepository.findAllByOrderByNameAsc();

        } catch (Exception e) {
            e.printStackTrace();
            log.error("CountryService | findAll | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void save(Country country) {
        log.debug("CountryService | save | country: " + country.toString());

        try {
            if (isExists(country.getName())) {
                throw new BusinessException("Country already exist", HttpStatus.BAD_REQUEST);
            }

            countryRepository.save(country);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("CountryService | save | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public void delete(Long id) {
        log.debug("CountryService | delete | id: " + id);

        try {
            countryRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("CountryService | delete | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public Boolean isExists(String name) {
        log.debug("CountryService | isExists | name: " + name);

        try {
            return countryRepository.existsByName(name);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("CountryService | isExists | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void update(Country country) {
        log.debug("CountryService | update | country: " + country.toString());

        try {
            countryRepository.save(country);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("CountryService | update | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
