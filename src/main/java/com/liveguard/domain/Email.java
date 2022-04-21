package com.liveguard.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Locale;

@Entity
@Table(name = "emails")
@Setter
@Getter
@NoArgsConstructor
public class Email extends BaseEntity {

    private String subject;

    @Column(length = 8192, nullable = false)
    private String content;
    private String receiver;
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
