package com.liveguard.repository;

import com.liveguard.domain.Role;
import com.liveguard.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    List<User> findAllByRoles(Role role);

    List<User> findAllByRolesAndEnableTrue(Role role);

    @Query("UPDATE User u SET u.enable = ?2 WHERE u.id = ?1")
    @Modifying
    void updateEnabledStatus(Long id, Boolean status);

    @Query("UPDATE User u SET u.accountNonExpired = ?2 WHERE u.id = ?1")
    @Modifying
    void updateAccountNonExpiredStatus(Long id, Boolean status);

    @Query("UPDATE User u SET u.accountNonLocked = ?2 WHERE u.id = ?1")
    @Modifying
    void updateAccountNonLockedStatus(Long id, Boolean status);

    @Query("UPDATE User u SET u.credentialsNonExpired = ?2 WHERE u.id = ?1")
    @Modifying
    void updateCredentialsNonExpiredStatus(Long id, Boolean status);


}
