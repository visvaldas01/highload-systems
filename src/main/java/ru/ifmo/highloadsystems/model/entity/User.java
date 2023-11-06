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
    private String login;

    @Column(nullable = false)
    private String password;

    @JsonIgnoreProperties(value = "users")
    @ManyToMany
    @JoinTable(name = "songs_users", joinColumns = @JoinColumn(name = "users_id"), inverseJoinColumns = @JoinColumn(name = "songs_id"))
    private Collection<Song> songs;

    @JsonIgnoreProperties(value = "users")
    @ManyToMany
    @JoinTable(name = "albums_users", joinColumns = @JoinColumn(name = "users_id"), inverseJoinColumns = @JoinColumn(name = "albums_id"))
    private Collection<Album> albums;

    @JsonIgnoreProperties(value = "users")
    @ManyToMany
    @JoinTable(name = "musicians_users", joinColumns = @JoinColumn(name = "users_id"), inverseJoinColumns = @JoinColumn(name = "musicians_id"))
    private Collection<Musician> musicians;
}