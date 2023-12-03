package ru.ifmo.highloadsystems.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@Entity
@Table(name = "albums")
@NoArgsConstructor
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @JsonIgnoreProperties(value = "albums")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "albums_musicians", joinColumns = @JoinColumn(name = "albums_id"), inverseJoinColumns = @JoinColumn(name = "musicians_id"))
    private Collection<Musician> musicians;

    @JsonIgnoreProperties(value = "albums")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "albums_tags", joinColumns = @JoinColumn(name = "albums_id"), inverseJoinColumns = @JoinColumn(name = "tags_id"))
    private Collection<Tag> tags;

    @JsonIgnoreProperties(value = "albums")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "albums_users", joinColumns = @JoinColumn(name = "albums_id"), inverseJoinColumns = @JoinColumn(name = "users_id"))
    private Collection<User> users;

    @JsonIgnoreProperties(value = "albums")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "albums_songs", joinColumns = @JoinColumn(name = "albums_id"), inverseJoinColumns = @JoinColumn(name = "songs_id"))
    private Collection<Song> songs;
}
