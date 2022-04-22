package com.liveguard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "verification_codes")
@Setter
@Getter
@NoArgsConstructor
public class VerificationCode extends BaseEntity {

    private String code;
    private LocalDateTime createDate;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return "VerificationCode{" +
                "id='" + super.getId() + '\'' +
                ", code='" + code + '\'' +
                ", createDate=" + createDate +
                ", user=" + user.toString() +
                '}';
    }
}
