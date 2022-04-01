package com.liveguard.repository;

import com.liveguard.domain.User;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Test
    void save() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setName("Mohamed Emad");
        user.setPassword(passwordEncoder.encode("MO0420"));
        user.setEmail("mohamed@gmail.com");
        user.getRoles().add(roleRepository.findById(1L).get());
        user.setEnable(true);
        user.setCreatedTime(LocalDateTime.now());
        user.setCredentialsNonExpired(true);
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);

        userRepository.save(user);
    }

    @Test
    void findByEmail() {
    }

    @Test
    void updateEnableStatus() {
        userRepository.updateEnableStatus(11L, true);
        User user = userRepository.findById(11L).get();

        System.out.println(user.toString());
        assertEquals(user.getEnable(), true);
    }


    @Test
    void updateResetPasswordToken() {

    }

    @Test
    void findByResetPasswordToken() {
        String token = "whrZ7EkW2H0v8YavegR4KZJvTRmFHv";
        User user = userRepository.findByResetPasswordToken(token).get();

        System.out.println(user.toString());
        assertEquals(user.getResetPasswordToken(), token);
    }

    @Test
    void updateAvatar() {
        String avatar = "/test.jpg";
        userRepository.updateAvatar(13L, avatar);

        User user = userRepository.findById(13L).get();

        System.out.println(user.toString());
        assertEquals(user.getAvatar(), avatar);
    }


}