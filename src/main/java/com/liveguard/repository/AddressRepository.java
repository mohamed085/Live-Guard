package com.liveguard.repository;

import com.liveguard.domain.Address;
import com.liveguard.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findAllByUser(User user);

    @Query("SELECT a FROM Address a WHERE a.id = ?1 AND a.user.id = ?2")
    Address findByIdAndCustomer(Long addressId, Long customerId);

    @Query("DELETE FROM Address a WHERE a.id = ?1 AND a.user.id = ?2")
    @Modifying
    void deleteByIdAndUser(Long addressId, Long customerId);

    @Query("UPDATE Address a SET a.defaultForShipping = true WHERE a.id = ?1")
    @Modifying
    void setDefaultAddress(Long id);

    @Query(value = "UPDATE Address a SET a.defaultForShipping = false "
            + "WHERE a.id <> ?1 AND a.user.id = ?2")
    @Modifying
    void setNonDefaultForOthers(Long defaultAddressId, Long userId);

    @Query("SELECT a FROM Address a WHERE a.user.id = ?1 AND a.defaultForShipping = true")
    Optional<Address> findDefaultByUser(Long userId);


}
