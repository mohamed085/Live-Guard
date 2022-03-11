package com.liveguard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tasks")
@Setter
@Getter
@NoArgsConstructor
public class Task extends BaseEntity {
    private String name;
    private String description;
    private String ringtone;
    private LocalTime startDate;
    private LocalTime endDate;
    private LocalDateTime createDate;
    private Double lat;
    private Double lng;
    private Double area;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "task_repeat",
            joinColumns = {@JoinColumn(name = "task_id")},
            inverseJoinColumns = {@JoinColumn(name = "day_id")}
    )
    private Set<Day> repeat = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User addByUser;

    @ManyToOne
    @JoinColumn(name = "chip_id")
    private Chip chip;

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", ringtone='" + ringtone + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", createDate=" + createDate +
                ", lat=" + lat +
                ", lng=" + lng +
                ", area=" + area +
                ", chip=" + chip.getId() +
                '}';
    }
}
