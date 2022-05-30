package com.liveguard.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "emails")
@Setter
@Getter
@NoArgsConstructor
public class Email extends BaseEntity {

    private String subject;
    @Column(length = 4096)
    private String content;
    private String receiver;
    @Enumerated(EnumType.STRING)
    private EmailSendStatus status;

    private LocalDateTime createdDate;

    private LocalDateTime sendDate;

    @Override
    public String toString() {
        return "Email{" +
                "id='" + super.getId() + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", receiver='" + receiver + '\'' +
                ", status=" + status +
                '}';
    }
}
