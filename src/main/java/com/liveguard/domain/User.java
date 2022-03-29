package com.liveguard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
public class User extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String avatar;
    private String phone;
    private String address;
    private String gender;

    private LocalDate dateOfBirth;
    private String postalCode;
    private LocalDateTime createdTime;
    private String resetPasswordToken;

    private Boolean enable;
    private Boolean accountNonExpired;
    private Boolean credentialsNonExpired;
    private Boolean accountNonLocked;

    private String facebookUrl;
    private String twitterUrl;
    private String instagramUrl;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> roles = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private AuthenticationType authenticationType;

    @Override
    public String toString() {
        return "User{" +
                "id='" + super.getId() + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", enable=" + enable +
                ", roles=" + roles +
                ", authenticationType=" + authenticationType +
                '}';
    }
}
