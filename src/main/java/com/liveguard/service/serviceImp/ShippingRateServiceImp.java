package com.liveguard.service.serviceImp;

import com.liveguard.domain.ShippingRate;
import com.liveguard.exception.BusinessException;
import com.liveguard.repository.ShippingRateRepository;
import com.liveguard.service.ShippingRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
public class ShippingRateServiceImp implements ShippingRateService {

    public static final int SHIPPING_RATE_PER_PAGE = 6;

    private final ShippingRateRepository shippingRateRepository;

    public ShippingRateServiceImp(ShippingRateRepository shippingRateRepository) {
        this.shippingRateRepository = shippingRateRepository;
    }

    @Override
    public Page<ShippingRate> findAllByPage(int pageNum, String sortField, String sortDir, String keyword) {
        log.debug("ShippingRateService | findAllByPage");
        log.debug("ShippingRateService | findAllByPage | pageNum: " + pageNum);
        log.debug("ShippingRateService | findAllByPage | sortField: " + sortField);
        log.debug("ShippingRateService | findAllByPage | sortDir: " + sortDir);
        log.debug("ShippingRateService | findAllByPage | keyword: " + keyword);

        if (sortField == null) {
            sortField = "id";
        }

        if (sortDir == null) {
            sortDir = "asc";
        }

        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1 , SHIPPING_RATE_PER_PAGE, sort);


        if (keyword == null) {
            return shippingRateRepository.findAll(pageable);
        } else {
            return shippingRateRepository.findAll(keyword, pageable);
        }
    }

    @Override
    public void save(ShippingRate shippingRate) {
        log.debug("ShippingRateService | save | shippingRate: " + shippingRate.toString());

        if (shippingRateRepository.existsByCountryIsIgnoreCaseAndStateIgnoreCase(shippingRate.getCountry(), shippingRate.getState())) {
            throw new BusinessException("Shipping rate already exist", HttpStatus.BAD_REQUEST);
        }

        shippingRateRepository.save(shippingRate);
    }

    @Override
    public ShippingRate findById(Long id) {
        log.debug("ShippingRateService | save | id: " + id);

        return shippingRateRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Not found", HttpStatus.NOT_FOUND));

    }

    @Override
    public void update(ShippingRate shippingRate) {
        log.debug("ShippingRateService | update | shippingRate: " + shippingRate.toString());

        ShippingRate oldShippingRate = shippingRateRepository.findByCountryAndState(shippingRate.getCountry(), shippingRate.getState()).get();
        if (!oldShippingRate.getId().equals(shippingRate.getId())) {
            throw new BusinessException("Shipping rate already exist", HttpStatus.BAD_REQUEST);
        }

        shippingRateRepository.save(shippingRate);
    }

    @Override
    @Transactional
    public void updateCODSupport(Long id, Boolean CODSupport) {
        log.debug("ShippingRateService | updateCODSupport | id: " + id);
        log.debug("ShippingRateService | updateCODSupport | CODSupport: " + CODSupport);

        shippingRateRepository.updateCODSupport(id, CODSupport);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.debug("ShippingRateService | delete | id: " + id);

        shippingRateRepository.deleteById(id);
    }
}
