package com.liveguard.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "users_tasks_mute")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UsersTasksMute extends BaseEntity {

    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return "UsersTasksMute{" +
                "status=" + status +
                ", task=" + task.getId() +
                ", user=" + user.getId() +
                '}';
    }
}