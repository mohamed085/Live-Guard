package com.liveguard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "shopping_cart")
@NoArgsConstructor
@Getter
@Setter
public class ShoppingCart extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    private int quantity;
    private float productsTotal;

    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ShoppingCartItem> shoppingCartItems;

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "customer=" + customer.getId() +
                ", quantity=" + quantity +
                ", productsTotal=" + productsTotal +
                ", shoppingCartItems=" + shoppingCartItems +
                '}';
    }
}
