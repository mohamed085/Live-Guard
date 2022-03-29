package com.liveguard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class Role extends BaseEntity {

    private String role;
    private String description;

    public Role(String role, String description) {
        this.role = role;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id='" + super.getId() + '\'' +
                ", role='" + role + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
