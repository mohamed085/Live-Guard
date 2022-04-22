package com.liveguard.repository;

import com.liveguard.domain.Role;
import com.liveguard.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByVerificationCode(String verificationCode);

    @Query("UPDATE User u SET u.verificationCode = ?2 WHERE u.id = ?1")
    @Modifying
    void updateVerificationCode(Long id, String verificationCode);

    @Query("UPDATE User u SET u.enable = ?2 WHERE u.id = ?1")
    @Modifying
    void updateEnableStatus(Long id, Boolean status);

    @Query("UPDATE User u SET u.resetPasswordToken = ?2 WHERE u.id = ?1")
    @Modifying
    void updateResetPasswordToken(Long id, String resetPasswordToken);

    Optional<User> findByResetPasswordToken(String token);

    @Query("UPDATE User u SET u.password = ?2 WHERE u.id = ?1")
    @Modifying
    void updatePassword(Long id, String password);

    @Query("UPDATE User u SET u.avatar = ?2 WHERE u.id = ?1")
    @Modifying
    void updateAvatar(Long id, String avatar);

    List<User> findAllByRoles(Role role);

    @Query("SELECT u FROM User u WHERE u.name LIKE %?1% OR u.email LIKE %?1%")
    Page<User> findAll(String keyword, Pageable pageable);
}
