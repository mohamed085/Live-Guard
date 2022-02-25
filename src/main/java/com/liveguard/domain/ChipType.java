package com.liveguard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "chip_types")
@Setter
@Getter
@NoArgsConstructor
public class ChipType extends BaseEntity {
    private String type;

    @OneToMany(mappedBy = "chipType")
    private Set<Chip> chips = new HashSet<>();

}
