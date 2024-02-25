package ru.ifmo.highloadsystems.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
public class Album {
    private Long id;

    private String name;

    @JsonIgnoreProperties("albums")
    private Collection<Musician> musicians;

    @JsonIgnoreProperties("albums")
    private Collection<Tag> tags;

    @JsonIgnoreProperties("albums")
    private Collection<User> users;

    @JsonIgnoreProperties("albums")
    private Collection<Song> songs;
}
