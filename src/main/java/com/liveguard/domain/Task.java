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
    private Boolean mute;
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
    private Set<TaskDay> repeat = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User addByUser;

    @ManyToOne
    @JoinColumn(name = "chip_id")
    private Chip chip;

    @OneToMany(mappedBy = "task")
    private Set<UsersTasksMute> usersTasksMutes = new HashSet<>();

    public String getStartDateInString() {
        return String.valueOf(startDate);
    }

    public String getEndDateInString() {
        return String.valueOf(endDate);
    }

    public String getCreateDateInString() {
        return String.valueOf(createDate);
    }

}
