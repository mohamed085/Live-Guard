package com.liveguard.service.serviceImp;

import com.liveguard.domain.Address;
import com.liveguard.domain.User;
import com.liveguard.dto.AddressDTO;
import com.liveguard.exception.BusinessException;
import com.liveguard.mapper.AddressMapper;
import com.liveguard.repository.AddressRepository;
import com.liveguard.service.AccountService;
import com.liveguard.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class AddressServiceImp implements AddressService {

    private final AddressRepository addressRepository;
    private final AccountService accountService;

    public AddressServiceImp(AddressRepository addressRepository, AccountService accountService) {
        this.addressRepository = addressRepository;
        this.accountService = accountService;
    }

    @Override
    public void save(AddressDTO addressDTO) {
        log.debug("AddressService | save | addressDTO: " + addressDTO);

        Address address = AddressMapper.addressDTOToAddress(addressDTO);
        address.setUser(accountService.getAuthenticatedAccount());
        address.setDefaultForShipping(false);

        addressRepository.save(address);
    }

    @Override
    public Address findById(Long addressId) {
        log.debug("AddressService | save | findById: " + addressId);

        return addressRepository.findById(addressId)
                .orElseThrow(() -> new BusinessException("Address not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Address> findMyAddresses() {
        User user = accountService.getAuthenticatedAccount();
        log.debug("AddressService | findMyAddresses | user: " + user.getEmail());

        return addressRepository.findAllByUser(user);
    }

    @Override
    public Address findMyDefaultAddress() {
        User user = accountService.getAuthenticatedAccount();
        log.debug("AddressService | findMyDefaultAddress | user: " + user.getEmail());

        return addressRepository.findDefaultByUser(user.getId())
                .orElseThrow(() -> new BusinessException("Address not found", HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public void setDefaultAddress(Long defaultAddressId) {
        User user = accountService.getAuthenticatedAccount();
        log.debug("AddressService | setDefaultAddress | user: " + user.getEmail());
        log.debug("AddressService | setDefaultAddress | defaultAddressId: " + defaultAddressId);

        addressRepository.setDefaultAddress(defaultAddressId);
        addressRepository.setNonDefaultForOthers(defaultAddressId, user.getId());
    }

    @Override
    public void updateAddress(AddressDTO addressDTO) {
        log.debug("AddressService | deleteAddress | addressDTO: " + addressDTO.toString());

        addressRepository.save(AddressMapper.addressDTOToAddress(addressDTO));
    }

    @Override
    public void deleteAddress(Long id) {
        log.debug("AddressService | deleteAddress | findById: " + id);

        addressRepository.deleteById(id);
    }
}
