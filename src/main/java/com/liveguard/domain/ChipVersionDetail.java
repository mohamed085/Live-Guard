package com.liveguard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "chip_version_details")
@NoArgsConstructor
@Getter
@Setter
public class ChipVersionDetail extends BaseEntity {
    private String name;
    private String value;

    @ManyToOne
    @JoinColumn(name = "chip_version_id")
    private ChipVersion chipVersion;

    public ChipVersionDetail(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public ChipVersionDetail(String name, String value, ChipVersion chipVersion) {
        this.name = name;
        this.value = value;
        this.chipVersion = chipVersion;
    }

    public ChipVersionDetail(Long id, String name, String value, ChipVersion chipVersion) {
        super(id);
        this.name = name;
        this.value = value;
        this.chipVersion = chipVersion;
    }

    @Override
    public String toString() {
        return "ChipVersionDetail{" +
                "id='" + super.toString() + '\'' +
                ", name='" + name + '\'' +
                ", value='" + value +
                '}';
    }
}
