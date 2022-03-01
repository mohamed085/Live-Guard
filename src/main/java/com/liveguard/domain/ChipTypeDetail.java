package com.liveguard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "chip_type_details")
@NoArgsConstructor
@Getter
@Setter
public class ChipTypeDetail extends BaseEntity {
    private String name;
    private String value;

    @ManyToOne
    @JoinColumn(name = "chip_type_id")
    private ChipType chipType;

    public ChipTypeDetail(String name, String value, ChipType chipType) {
        this.name = name;
        this.value = value;
        this.chipType = chipType;
    }

    public ChipTypeDetail(Long id, String name, String value, ChipType chipType) {
        super(id);
        this.name = name;
        this.value = value;
        this.chipType = chipType;
    }
}
