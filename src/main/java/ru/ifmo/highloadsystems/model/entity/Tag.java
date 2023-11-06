package ru.ifmo.highloadsystems.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@Entity
@Table(name = "tags")
@NoArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @JsonIgnoreProperties(value = "tags")
    @ManyToMany
    @JoinTable(name = "songs_tags", joinColumns = @JoinColumn(name = "tags_id"), inverseJoinColumns = @JoinColumn(name = "songs_id"))
    private Collection<Song> songs;

    @JsonIgnoreProperties(value = "tags")
    @ManyToMany
    @JoinTable(name = "albums_tags", joinColumns = @JoinColumn(name = "tags_id"), inverseJoinColumns = @JoinColumn(name = "albums_id"))
    private Collection<Album> albums;

    @JsonIgnoreProperties(value = "tags")
    @ManyToMany
    @JoinTable(name = "musicians_tags", joinColumns = @JoinColumn(name = "tags_id"), inverseJoinColumns = @JoinColumn(name = "musicians_id"))
    private Collection<Musician> musicians;
}