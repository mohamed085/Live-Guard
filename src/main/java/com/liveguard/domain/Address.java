package com.liveguard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
@NoArgsConstructor
@Getter
@Setter
public class Address extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;

    @Column(name = "address_line", nullable = false, length = 64)
    private String addressLine;

    @Column(nullable = false, length = 45)
    private String city;

    @Column(nullable = false, length = 45)
    private String state;

    @Column(nullable = false, length = 45)
    private String country;

    @Column(name = "default_address")
    private Boolean defaultForShipping;

    @Override
    public String toString() {
        return "Address{" +
                "user=" + user.getEmail() +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", addressLine='" + addressLine + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", defaultForShipping=" + defaultForShipping +
                '}';
    }
}
