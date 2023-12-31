package com.vmo.freshermanagement.intern.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @ManyToOne
    @JoinColumn(name = "authority_id")
    private Authority authority;

    @Column(name = "password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "is_not_locked")
    private boolean isNotLocked;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.authority = new Authority();
        this.authority.setId(2);
        this.setNotLocked(true);
    }
}
