package com.liveguard.service;

import com.liveguard.dto.UserDTO;
import com.liveguard.dto.SimpleUserDTO;

import java.util.List;

public interface UserServices {

    UserDTO findById(Long id);

    List<SimpleUserDTO> findAllCustomersInSimpleForm();
}
