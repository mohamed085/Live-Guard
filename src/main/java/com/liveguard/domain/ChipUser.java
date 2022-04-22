package com.liveguard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chip_users")
@NoArgsConstructor
@Getter
@Setter
public class ChipUser extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "chip_id")
    private Chip chip;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime addDate;

    @Enumerated(EnumType.STRING)
    private ChipUserType chipUserType;

    public ChipUser(Chip chip, User user, LocalDateTime addDate, ChipUserType chipUserType) {
        this.chip = chip;
        this.user = user;
        this.addDate = addDate;
        this.chipUserType = chipUserType;
    }

    @Override
    public String toString() {
        return "ChipUser{" +
                "chip=" + chip.getName() +
                ", user=" + user.getEmail() +
                ", addDate=" + addDate +
                ", chipUserType=" + chipUserType +
                '}';
    }
}
