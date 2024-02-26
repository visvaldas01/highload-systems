package ru.ifmo.highloadsystems.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
public class User {
    private Long id;

    private String username;

    @JsonIgnore
    private String password;

    @JsonIgnoreProperties("roles")
    private Collection<Role> roles;

    @JsonIgnoreProperties("songs")
    private Collection<Song> songs;

    @JsonIgnoreProperties("albums")
    private Collection<Album> albums;

    @JsonIgnoreProperties("musicians")
    private Collection<Musician> musicians;
}