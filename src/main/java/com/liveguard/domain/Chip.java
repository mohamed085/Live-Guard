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

    private String name;
    private String photo;
    private String password;
    private Boolean used;

    @OneToMany(mappedBy = "chip", cascade = CascadeType.ALL)
    private List<ChipDetail> details;

    @OneToMany(mappedBy = "chip", cascade = CascadeType.ALL)
    private List<ChipUser> users;

    @ManyToOne
    @JoinColumn(name="chip_version_id", nullable = false)
    private ChipVersion chipVersion;

    @Override
    public String toString() {
        return "Chip{" +
                "id='" + super.getId() + '\'' +
                ", name='" + name + '\'' +
                ", photo='" + photo + '\'' +
                ", password='" + password + '\'' +
                ", used=" + used +
                ", details=" + details +
                ", users=" + users +
                ", chipVersion=" + chipVersion +
                '}';
    }
}
