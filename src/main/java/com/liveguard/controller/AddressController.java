package com.liveguard.controller;

import com.liveguard.dto.AddressDTO;
import com.liveguard.mapper.AddressMapper;
import com.liveguard.payload.ApiResponse;
import com.liveguard.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/address_book")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("")
    public ResponseEntity<?> findMyAddresses() {
        log.debug("AddressController | findMyAddresses");

        List<AddressDTO> addresses = new ArrayList<>();
        addressService.findMyAddresses().forEach(address -> {
            addresses.add(AddressMapper.addressToAddressDTO(address));
        });
        return ResponseEntity
                .ok()
                .body(addresses);
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody AddressDTO addressDTO) {
        log.debug("AddressController | save | addressDTO: " + addressDTO);

        addressService.save(addressDTO);
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Address added successfully"));
    }

    @PutMapping("")
    public ResponseEntity<?> update(@RequestBody AddressDTO addressDTO) {
        log.debug("AddressController | save | addressDTO: " + addressDTO);

        addressService.save(addressDTO);
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Address updated successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        log.debug("AddressController | getById | id: " + id);

        return ResponseEntity
                .ok()
                .body(AddressMapper.addressToAddressDTO(addressService.findById(id)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        log.debug("AddressController | delete | id: " + id);

        addressService.deleteAddress(id);
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Address deleted successfully"));
    }

    @GetMapping("/default/{id}")
    public ResponseEntity<?> setDefaultAddress(@PathVariable("id") Long id) {
        log.debug("AddressController | setDefaultAddress | id: " + id);

        addressService.setDefaultAddress(id);
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Address set default successfully"));
    }
}
