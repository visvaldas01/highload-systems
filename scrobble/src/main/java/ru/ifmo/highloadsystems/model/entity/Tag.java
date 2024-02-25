package ru.ifmo.highloadsystems.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
public class Tag {
    private Long id;

    private String name;

    @JsonIgnoreProperties("tags")
    private Collection<Song> songs;

    @JsonIgnoreProperties("tags")
    private Collection<Album> albums;

    @JsonIgnoreProperties("tags")
    private Collection<Musician> musicians;

    @JsonIgnoreProperties("tags")
    private TagGroup tagGroup;
}