package com.liveguard.repository;

import com.liveguard.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

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