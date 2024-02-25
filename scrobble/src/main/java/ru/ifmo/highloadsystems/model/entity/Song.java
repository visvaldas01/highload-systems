package ru.ifmo.highloadsystems.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Song {
    private Long id;

    private String name;

    private Float vector1;

    private Float vector2;

    private Float vector3;

    @JsonIgnoreProperties("songs")
    private Collection<Musician> musicians;

    @JsonIgnoreProperties("songs")
    private Collection<Tag> tags;

    @JsonIgnoreProperties("songs")
    private Collection<User> users;

    @JsonIgnoreProperties("songs")
    private Collection<Album> albums;
}