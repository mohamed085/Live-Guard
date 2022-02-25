package com.liveguard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "notifications")
@Setter
@Getter
@NoArgsConstructor
public class Notification extends BaseEntity {
    private String sender;
    private String content;
    private String photo;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
