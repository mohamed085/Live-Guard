package com.liveguard.mapper;

import com.liveguard.domain.Address;
import com.liveguard.dto.AddressDTO;

public class AddressMapper {

    public static Address addressDTOToAddress(AddressDTO addressDTO) {
        Address address = new Address();

        address.setId(addressDTO.getId());
        address.setPhoneNumber(addressDTO.getPhoneNumber());
        address.setAddressLine(addressDTO.getAddressLine());
        address.setCity(addressDTO.getCity());
        address.setCountry(addressDTO.getCountry());
        address.setState(addressDTO.getState());
        address.setDefaultForShipping(addressDTO.getDefaultForShipping());

        return address;
    }

    public static AddressDTO addressToAddressDTO(Address address) {
        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setId(address.getId());
        addressDTO.setPhoneNumber(address.getPhoneNumber());
        addressDTO.setAddressLine(address.getAddressLine());
        addressDTO.setCity(address.getCity());
        addressDTO.setCountry(address.getCountry());
        addressDTO.setState(address.getState());
        addressDTO.setDefaultForShipping(address.getDefaultForShipping());

        return addressDTO;
    }
}
