package com.liveguard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "shipping_rates")
@NoArgsConstructor
@Getter
@Setter
public class ShippingRate extends BaseEntity {

    private float rate;

    private int days;

    @Column(name = "cod_supported")
    private boolean codSupported;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @Column(nullable = false, length = 45)
    private String state;

}
