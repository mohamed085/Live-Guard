package com.liveguard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "store")
@Setter
@Getter
@NoArgsConstructor
public class Store extends BaseEntity {

    private String name;
    private String description;
    private String phone;
    private Double rate;

    private String address;
    private Double lng;
    private Double lat;

    private LocalTime openTime;
    private LocalTime closeTime;

    private Boolean delivery;


    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<StoreImage> images = new ArrayList<>();

}
