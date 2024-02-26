package ru.ifmo.highloadsystems.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
public class Musician {
    private Long id;

    private String name;

    @JsonIgnoreProperties("musicians")
    private Collection<Song> songs;

    @JsonIgnoreProperties("musicians")
    private Collection<Album> albums;

    @JsonIgnoreProperties("musicians")
    private Collection<Tag> tags;

    @JsonIgnoreProperties("musicians")
    private Collection<User> users;
}