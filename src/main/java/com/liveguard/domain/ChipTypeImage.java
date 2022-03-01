package com.liveguard.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "chip_type_images")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChipTypeImage extends BaseEntity {

    private String name;

    @ManyToOne
    @JoinColumn(name = "chip_type_id")
    private ChipType chipType;

}
