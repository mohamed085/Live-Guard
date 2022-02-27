package com.liveguard.repository;

import com.liveguard.domain.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testAddNewRole() {
        Role role = new Role();
        role.setRole("user");
        role.setDescription("Default role");

        Role savedRole = roleRepository.save(role);

        System.out.println(savedRole.getId() + " " + savedRole.getRole() + " " + savedRole.getDescription());
        assertNotNull(savedRole);
    }
}