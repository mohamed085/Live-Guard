package com.liveguard.repository;

import com.liveguard.domain.Currency;
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
@Rollback(value = false)
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testAddNewRole() {
        Role role = new Role();
        role.setRole("admin");
        role.setDescription("");

        Role savedRole = roleRepository.save(role);

        System.out.println(savedRole.getId() + " " + savedRole.getRole() + " " + savedRole.getDescription());
        assertNotNull(savedRole);
    }

    @Test
    public void testAddListOfRole() {
        List<Role> roles = Arrays.asList(
                new Role("customer", ""),
                new Role("vendor", ""),
                new Role("admin", "")
        );

        roleRepository.saveAll(roles);

        List<Role> iterable = roleRepository.findAll();
        System.out.println(iterable);
        assertNotNull(iterable);

    }

    @Test
    public void findAll() {
        List<Role> roles = roleRepository.findAll();

        System.out.println(roles);
        assertNotNull(roles);
    }
    
    @Test
    public void findById() {
        Role role = roleRepository.findById(1L).get();
        System.out.println(role);
        assertEquals(role.getId(), 1L);
    }
}