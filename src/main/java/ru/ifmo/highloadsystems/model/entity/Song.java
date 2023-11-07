package ru.ifmo.highloadsystems.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Builder
@Data
@Entity
@Table(name = "songs")
@NoArgsConstructor
@AllArgsConstructor
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private Float vector1;

    @Column
    private Float vector2;

    @Column
    private Float vector3;

    @JsonIgnoreProperties(value = "songs")
    @ManyToMany
    @JoinTable(name = "songs_musicians", joinColumns = @JoinColumn(name = "songs_id"), inverseJoinColumns = @JoinColumn(name = "musicians_id"))
    private Collection<Musician> musicians;

    @JsonIgnoreProperties(value = "songs")
    @ManyToMany
    @JoinTable(name = "songs_tags", joinColumns = @JoinColumn(name = "songs_id"), inverseJoinColumns = @JoinColumn(name = "tags_id"))
    private Collection<Tag> tags;

    @JsonIgnoreProperties(value = "songs")
    @ManyToMany
    @JoinTable(name = "songs_users", joinColumns = @JoinColumn(name = "songs_id"), inverseJoinColumns = @JoinColumn(name = "users_id"))
    private Collection<User> users;

    @JsonIgnoreProperties(value = "songs")
    @ManyToMany
    @JoinTable(name = "albums_songs", joinColumns = @JoinColumn(name = "songs_id"), inverseJoinColumns = @JoinColumn(name = "albums_id"))
    private Collection<Album> albums;
}