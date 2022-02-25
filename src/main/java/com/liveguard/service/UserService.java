package com.liveguard.service;

import com.liveguard.domain.User;

public interface UserService {

    User findByEmail(String email);

    User findById(Long id);

    Boolean userExistByEmail(String email);

    User save(User user);

}
