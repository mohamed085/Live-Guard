package com.liveguard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "task_days")
@Setter
@Getter
@NoArgsConstructor
public class TaskDay extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private Day day;

    @ManyToMany(mappedBy = "repeat")
    private Set<Task> tasks = new HashSet<>();
}
