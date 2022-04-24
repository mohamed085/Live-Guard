package com.liveguard.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @Column(nullable = false, length = 45)
    private String country;

    @Column(nullable = false, length = 45)
    private String state;

    private float rate;

    private int days;

    @JsonProperty("cod_supported")
    @Column(name = "cod_supported")
    private boolean codSupported;

    @Override
    public String toString() {
        return "ShippingRateController{" +
                "id='" + super.getId() + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", rate=" + rate +
                ", days=" + days +
                ", codSupported=" + codSupported +
                '}';
    }
}
