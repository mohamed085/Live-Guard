package com.liveguard.repository;

import com.liveguard.domain.ShoppingCartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem, Long> {

    Boolean existsByChipVersionIdAndAndShoppingCartId(Long chipVersionId, Long shoppingCartId);

    ShoppingCartItem findByChipVersionIdAndShoppingCartId(Long chipVersionId, Long shoppingCartId);

    @Query("UPDATE ShoppingCartItem sci SET sci.quantity = ?2 WHERE sci.id = ?1")
    @Modifying
    void updateQuantity(Long id, int quantity);

    @Query("UPDATE ShoppingCartItem sci SET sci.total = ?2 WHERE sci.id = ?1")
    @Modifying
    void updateTotal(Long id, float total);

    @Query("UPDATE ShoppingCartItem sci SET sci.quantity = ?2, sci.total = ?3 WHERE sci.id = ?1")
    @Modifying
    void updateInfo(Long id, int quantity, float total);

    List<ShoppingCartItem> findAllByShoppingCartId(Long id);

}
