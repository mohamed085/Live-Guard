package com.liveguard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class Role extends BaseEntity {

    private String role;
    private String description;

    @ManyToMany(mappedBy = "roles")
    private Set<User> Users = new HashSet<>();

    public Role(String role, String description) {
        this.role = role;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Role{" +
                "role='" + role + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
