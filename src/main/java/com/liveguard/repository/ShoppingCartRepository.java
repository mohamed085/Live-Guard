package com.liveguard.repository;

import com.liveguard.domain.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    Optional<ShoppingCart> findByCustomerId(Long id);

    Boolean existsByCustomerId(Long id);

    @Query("UPDATE ShoppingCart sc SET sc.quantity = ?2 ,sc.productsTotal = ?3 WHERE sc.id = ?1")
    @Modifying
    void updateInfo(Long shoppingCartId, int quantity, float productsTotal);
}
