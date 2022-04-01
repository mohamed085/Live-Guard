package com.liveguard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "contact_messages")
@Setter
@Getter
@NoArgsConstructor
public class ContactMessage extends BaseEntity {
    
    private String name;
    private String email;
    private Boolean reply;

    @Column(length = 4096)
    private String content;
}
