package com.liveguard.repository;

import com.liveguard.domain.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class RoleRepositoryTest {

    @Autowired
    RoleRepository roleRepository;

    @Test
    void save() {
        Role roleAdmin = new Role("Admin", "manage everything");
        Role savedRole = roleRepository.save(roleAdmin);

        System.out.println(savedRole.toString());
        assertEquals(savedRole.getRole(), roleAdmin.getRole());
    }

    @Test
    void saveAll() {
        List<Role> roles = Arrays.asList(
                new Role("Customer", "users that use applications"),
                new Role("Shipper", "view products, view orders and update order status"),
                new Role("Assistant", "manage questions and reviews"),
                new Role("Salesperson", "manage chip versions and price")
        );

        List<Role> savedRoles = roleRepository.saveAll(roles);
        System.out.println(savedRoles.toString());
        assertEquals(savedRoles.size(), roles.size());
    }

    @Test
    void findAll() {
        List<Role> roles = roleRepository.findAll();

        System.out.println(roles.toString());
        assertNotNull(roles);
    }


}