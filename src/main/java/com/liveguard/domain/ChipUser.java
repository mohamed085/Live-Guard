package com.liveguard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "chip_users")
@NoArgsConstructor
@Getter
@Setter
public class ChipUser extends BaseEntity {

    private String name;
    private String photo;
    private LocalDateTime addDate;
    @ManyToOne
    @JoinColumn(name = "chip_id")
    private Chip chip;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "chipUser", cascade = CascadeType.ALL)
    private List<ChipUserDetail> details;

    @Enumerated(EnumType.STRING)
    private ChipUserType chipUserType;

    @Override
    public String toString() {
        return "ChipUser{" +
                "chip=" + chip.getId() +
                ", user=" + user.getEmail() +
                ", addDate=" + addDate +
                ", name='" + name + '\'' +
                ", photo='" + photo + '\'' +
                ", details=" + details +
                ", chipUserType=" + chipUserType +
                '}';
    }
}
