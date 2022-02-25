package com.liveguard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "chip_associated_details")
@Setter
@Getter
@NoArgsConstructor
public class ChipAssociatedDetails extends BaseEntity {
    private String name;
    private String age;
    private String phone;
    private String photo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User addByUser;

    @OneToOne(mappedBy = "chipAssociatedDetails")
    private Chip chip;
}
