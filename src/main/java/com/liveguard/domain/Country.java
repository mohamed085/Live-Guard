package com.liveguard.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "countries")
@NoArgsConstructor
@Getter
@Setter
public class Country extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String code;


    public Country(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public Country(Long id, String name, String code) {
        super.setId(id);
        this.name = name;
        this.code = code;
    }

    public Country(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Country [id=" + super.getId() + ", name=" + name + ", code=" + code + "]";
    }

}
