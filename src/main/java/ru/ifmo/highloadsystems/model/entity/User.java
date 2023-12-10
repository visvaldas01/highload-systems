package ru.ifmo.highloadsystems.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    @JsonIgnoreProperties(value = "password")
    private String password;

    @ManyToMany
    @JsonIgnoreProperties(value = "roles")
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    @ManyToMany
    @JsonIgnoreProperties(value = "songs")
    @JoinTable(name = "songs_users", joinColumns = @JoinColumn(name = "users_id"), inverseJoinColumns = @JoinColumn(name = "songs_id"))
    private Collection<Song> songs;

    @JsonIgnoreProperties(value = "albums")
    @ManyToMany
    @JoinTable(name = "albums_users", joinColumns = @JoinColumn(name = "users_id"), inverseJoinColumns = @JoinColumn(name = "albums_id"))
    private Collection<Album> albums;

    @JsonIgnoreProperties(value = "musicians")
    @ManyToMany
    @JoinTable(name = "musicians_users", joinColumns = @JoinColumn(name = "users_id"), inverseJoinColumns = @JoinColumn(name = "musicians_id"))
    private Collection<Musician> musicians;
}