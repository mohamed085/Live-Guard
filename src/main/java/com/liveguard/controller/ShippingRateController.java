package com.liveguard.controller;

import com.liveguard.domain.ShippingRate;
import com.liveguard.payload.ApiResponse;
import com.liveguard.service.ShippingRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/shipping_rates")
public class ShippingRateController {

    private final ShippingRateService shippingRateService;

    public ShippingRateController(ShippingRateService shippingRateService) {
        this.shippingRateService = shippingRateService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        return getAllByPage(1, "id", "asc", null);
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody ShippingRate shippingRate) {
        shippingRateService.save(shippingRate);

        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Shipping rate saved successfully"));
    }

    @GetMapping("/page/{pageNum}")
    public ResponseEntity<?> getAllByPage(@PathVariable(name = "pageNum") int pageNum,
                                          @Param("sortField") String sortField,
                                          @Param("sortDir") String sortDir,
                                          @Param("keyword") String keyword) {

        log.debug("UserController | getAllByPage");
        log.debug("UserController | getAllByPage | pageNum: " + pageNum);
        log.debug("UserController | getAllByPage | sortField: " + sortField);
        log.debug("UserController | getAllByPage | sortDir: " + sortDir);
        log.debug("UserController | getAllByPage | keyword: " + keyword);

        return ResponseEntity
                .ok()
                .body(shippingRateService.findAllByPage(pageNum, sortField, sortDir, keyword));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        log.debug("UserController | getById | id: " + id);
        return ResponseEntity
                .ok()
                .body(shippingRateService.findById(id));
    }

    @PutMapping("")
    public ResponseEntity<?> update(@RequestBody ShippingRate shippingRate) {
        log.debug("UserController | update | shippingRate: " + shippingRate.toString());

        shippingRateService.update(shippingRate);

        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Shipping rate updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        log.debug("UserController | deleteById | id: " + id);

        shippingRateService.delete(id);

        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Shipping rate deleted successfully"));
    }

    @GetMapping("/cod/{id}/enabled/{supported}")
    public ResponseEntity<?> updateCODSupport(@PathVariable(name = "id") Long id,
                                   @PathVariable(name = "supported") Boolean supported) {

        shippingRateService.updateCODSupport(id, supported);

        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Shipping rate updated COD support successfully"));

    }

}
