package com.liveguard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "chips")
@Setter
@Getter
@NoArgsConstructor
public class Chip extends BaseEntity {
    private String name;
    private String photo;
    private String password;

    @ManyToOne
    @JoinColumn(name="chip_type_id", nullable=false)
    private ChipType chipType;

    @ManyToMany(mappedBy = "chips")
    private Set<User> users = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "chip_associated_details_id")
    private ChipAssociatedDetails chipAssociatedDetails;

    @OneToMany(mappedBy = "chip")
    private Set<Task> tasks = new HashSet<>();

}
