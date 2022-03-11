package com.liveguard.service;

import com.liveguard.domain.User;
import com.liveguard.dto.UserDTO;

import java.util.List;

public interface UserService {

    User findByEmail(String email);

    User findById(Long id);

    Boolean userExistByEmail(String email);

    User save(User user);

    List<User> getAllVendors();

    List<User> getAllEnableVendors();

    List<User> getAllCustomers();

    void addVendor(UserDTO userDTO);

    void updateEnabledStatus(Long id, Boolean status);

    void updateAccountNonExpiredStatus(Long id, Boolean status);

    void updateAccountNonLockedStatus(Long id, Boolean status);

    void updateCredentialsNonExpiredStatus(Long id, Boolean status);

}
