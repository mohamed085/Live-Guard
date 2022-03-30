package com.liveguard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "chip_version_images")
@NoArgsConstructor
@Getter
@Setter
public class ChipVersionImage  extends BaseEntity {
    private String photo;

    @ManyToOne
    @JoinColumn(name = "chip_version_id")
    private ChipVersion chipVersion;

    @Override
    public String toString() {
        return "ChipVersionImage{" +
                "id='" + super.getId() + '\'' +
                ", photo='" + photo + '\'' +
                ", chipVersion=" + chipVersion +
                '}';
    }
}
