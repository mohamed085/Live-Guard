package com.liveguard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "chips")
@Setter
@Getter
@NoArgsConstructor
public class Chip extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String key;

    @Column(nullable = false)
    private String password;
    private Boolean used;
    @ManyToOne
    @JoinColumn(name="chip_version_id", nullable = false)
    private ChipVersion chipVersion;

    @Override
    public String toString() {
        return "Chip{" +
                "id='" + super.getId() + '\'' +
                ", password='" + password + '\'' +
                ", used=" + used +
                ", chipVersion=" + chipVersion +
                '}';
    }
}
