package com.liveguard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "chip_details")
@NoArgsConstructor
@Getter
@Setter
public class ChipDetail extends BaseEntity {

    private String name;
    private String value;

    @ManyToOne
    @JoinColumn(name = "chip_id")
    private Chip chip;

    public ChipDetail(Long id, String name, String value, Chip chip) {
        super(id);
        this.name = name;
        this.value = value;
        this.chip = chip;
    }

    @Override
    public String toString() {
        return "ChipDetail{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
