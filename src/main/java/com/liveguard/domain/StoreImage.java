package com.liveguard.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "store_images")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StoreImage extends BaseEntity {

    private String name;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;


}
