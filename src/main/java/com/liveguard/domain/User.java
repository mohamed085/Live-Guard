package com.liveguard.domain;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "chips_users",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "chip_id")}
    )
    private Set<Chip> chips = new HashSet<>();

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(String name, String email, String password, Boolean enable, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.enable = enable;
        this.roles.add(role);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", createdTime=" + createdTime +
                ", enable=" + enable +
                ", accountNonExpired=" + accountNonExpired +
                ", credentialsNonExpired=" + credentialsNonExpired +
                ", accountNonLocked=" + accountNonLocked +
                ", roles=" + roles +
                ", authenticationType=" + authenticationType +
                '}' + '\n';
    }
}

