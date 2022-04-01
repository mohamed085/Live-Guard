package com.liveguard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "days")
@Setter
@Getter
@NoArgsConstructor
public class Day extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private EnumDay day;

    public Day(Long id, EnumDay day) {
        super(id);
        this.day = day;
    }

    public Day(EnumDay day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "Day{" +
                "day=" + day +
                '}';
    }
}
