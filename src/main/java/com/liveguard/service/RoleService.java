package com.liveguard.service;

import com.liveguard.domain.Role;

import java.util.List;

public interface RoleService {

    Role findById(Long id);

    Role findByRole(String role);

    List<Role> findAll();
}
