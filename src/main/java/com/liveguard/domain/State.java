package com.liveguard.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "states")
@NoArgsConstructor
@Getter
@Setter
public class State extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    public State(String name, Country country) {
        this.name = name;
        this.country = country;
    }

    @Override
    public String toString() {
        return "State [id=" + super.getId() + ", name=" + name + "]";
    }


}
