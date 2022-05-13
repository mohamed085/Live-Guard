package com.liveguard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "chip_user_details")
@NoArgsConstructor
@Getter
@Setter
public class ChipUserDetail extends BaseEntity {

    private String name;
    private String value;

    @ManyToOne
    @JoinColumn(name = "chip_user_id")
    private ChipUser chipUser;

    public ChipUserDetail(Long id, String name, String value, ChipUser chipUser) {
        super(id);
        this.name = name;
        this.value = value;
        this.chipUser = chipUser;
    }

    @Override
    public String toString() {
        return "ChipDetail{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
