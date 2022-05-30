package com.liveguard.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "chip_user_tasks")
@Setter
@Getter
@NoArgsConstructor
public class ChipUserTask extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private ChipUserTaskStatus status;

    @ManyToOne
    @JoinColumn(name = "chip_user_id")
    private ChipUser chipUser;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;


    @Override
    public String toString() {
        return "ChipUserTask{" +
                "status=" + status +
                ", chipUser=" + chipUser.getId() +
                ", task=" + task.getId() +
                '}';
    }
}
