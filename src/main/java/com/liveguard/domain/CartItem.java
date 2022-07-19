package com.liveguard.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cart_items")
@Data
@NoArgsConstructor
public class CartItem extends BaseEntity {
}
