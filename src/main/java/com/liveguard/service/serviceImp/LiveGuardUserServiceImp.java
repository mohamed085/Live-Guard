package com.liveguard.service.serviceImp;

import com.liveguard.domain.LiveGuardUserDetails;
import com.liveguard.repository.UserRepository;
import com.liveguard.service.LiveGuardUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LiveGuardUserServiceImp implements LiveGuardUserService {

    private final UserRepository userRepository;

    public LiveGuardUserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        log.debug("LiveGuardUserService | loadUserByUsername | find user: " + email);

        return userRepository
                .findByEmail(email)
                .map(LiveGuardUserDetails:: new)
                .orElseThrow(() -> new UsernameNotFoundException("Could not find user with email: " + email));
    }
}
