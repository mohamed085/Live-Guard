package com.liveguard.service;

import com.liveguard.dto.UserDTO;
import com.liveguard.dto.SimpleUserDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserServices {

    UserDTO findById(Long id);

    List<SimpleUserDTO> findAllCustomersInSimpleForm();

    Page<UserDTO> findAllByPage(int pageNum, String sortField, String sortDir, String keyword);
}
