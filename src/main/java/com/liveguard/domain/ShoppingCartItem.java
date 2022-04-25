package com.liveguard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "shopping_cart_items")
@NoArgsConstructor
@Getter
@Setter
public class ShoppingCartItem extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "chip_version_id")
    private ChipVersion chipVersion;

    private int quantity;
    private float total;

    @ManyToOne
    @JoinColumn(name = "shopping_cart_id")
    private ShoppingCart shoppingCart;

    @Override
    public String toString() {
        return "ShoppingCartItem{" +
                "chipVersion=" + chipVersion.getId() +
                ", quantity=" + quantity +
                ", total=" + total +
                ", shoppingCart=" + shoppingCart.getId() +
                '}';
    }
}
