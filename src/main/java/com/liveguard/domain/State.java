package com.liveguard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "states")
@NoArgsConstructor
@Getter
@Setter
public class State extends BaseEntity {

    @Column(nullable = false, length = 45)
    private String name;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;


    public State(String name, Country country) {
        this.name = name;
        this.country = country;
    }

    @Override
    public String toString() {
        return "State{" +
                "name='" + name + '\'' +
                ", country=" + country +
                '}';
    }
}
