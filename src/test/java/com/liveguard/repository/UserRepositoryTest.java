package com.liveguard.repository;

import com.liveguard.domain.Role;
import com.liveguard.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testAddUser() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User user = new User();
        user.setId(1L);
        user.setName("Mohamed Emad");
        user.setEmail("mohamed@gmail.com");
        user.setPassword(passwordEncoder.encode("MO0420"));
        user.setEnable(true);
        user.setCreatedTime(LocalDateTime.now());
        user.setCredentialsNonExpired(true);
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.getRoles().add(roleRepository.findById(3L).get());

        User savedUser = userRepository.save(user);
        System.out.println(savedUser);

        assertEquals(savedUser.getEmail(), user.getEmail());
    }

    @Test
    public void testSaveAll() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        List<User> users = Arrays.asList(
                new User("customer", "customer@gmail.com", passwordEncoder.encode("customer"), true, roleRepository.findById(1L).get()),
                new User("vendor", "vendor@gmail.com", passwordEncoder.encode("vendor"), true, roleRepository.findById(2L).get()),
                new User("admin", "admin@gmail.com", passwordEncoder.encode("admin"), true, roleRepository.findById(3L).get())
        );
        userRepository.saveAll(users);

        List<User> users1 = userRepository.findAll();
        System.out.println(users1);
        assertNotNull(users1);

    }

    @Test
    public void testFindAll() {
        List<User> users = userRepository.findAll();

        System.out.println(users);
        assertNotNull(users);
    }

    @Test
    public void testFindByRole() {
        List<User> users = userRepository.findAllByRoles(roleRepository.findById(1L).get());

        System.out.println(users);
        assertNotNull(users);

    }
}