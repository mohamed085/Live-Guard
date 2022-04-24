package com.liveguard.service;

import com.liveguard.domain.ShippingRate;
import org.springframework.data.domain.Page;

public interface ShippingRateService {

    Page<ShippingRate> findAllByPage(int pageNum, String sortField, String sortDir, String keyword);

    void save(ShippingRate shippingRate);

    ShippingRate findById(Long id);

    void update(ShippingRate shippingRate);

    void updateCODSupport(Long id, Boolean CODSupport);

    void delete(Long id);
}
