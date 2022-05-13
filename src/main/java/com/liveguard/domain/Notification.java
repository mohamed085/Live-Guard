package com.liveguard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
public class Notification extends BaseEntity {

    private String subject;
    @Column(length = 4096)
    private String content;

    private String ringtone;
    private String photo;
    private LocalDateTime createdTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return "Notification{" +
                "id='" + super.getId() + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", ringtone='" + ringtone + '\'' +
                ", photo='" + photo + '\'' +
                ", createdTime=" + createdTime +
                '}';
    }
}
