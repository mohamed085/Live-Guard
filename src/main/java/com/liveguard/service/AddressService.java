package com.liveguard.service;

import com.liveguard.domain.Address;
import com.liveguard.dto.AddressDTO;

import java.util.List;

public interface AddressService {

    void save(AddressDTO addressDTO);

    Address findById(Long addressId);

    List<Address> findMyAddresses();

    Address findMyDefaultAddress();

    void setDefaultAddress(Long defaultAddressId);

    void updateAddress(AddressDTO addressDTO);

    void deleteAddress(Long id);
}
