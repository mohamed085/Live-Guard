package com.liveguard.service;

import com.liveguard.domain.User;

public interface UserService {

    void save(User user);

    Boolean existsByEmail(String email);
}
