package com.liveguard.service;

import com.liveguard.domain.Role;

import java.util.List;

public interface RoleService {

    Role findById(Long id);

    List<Role> findAll();
}
