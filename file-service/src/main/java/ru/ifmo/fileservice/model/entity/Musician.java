package ru.ifmo.fileservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@Entity
@Table(name = "musicians")
@NoArgsConstructor
public class Musician {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @JsonIgnoreProperties("musicians")
    @ManyToMany
    @JoinTable(name = "songs_musicians", joinColumns = @JoinColumn(name = "musicians_id"), inverseJoinColumns = @JoinColumn(name = "songs_id"))
    private Collection<Song> songs;

    @JsonIgnoreProperties("musicians")
    @ManyToMany
    @JoinTable(name = "albums_musicians", joinColumns = @JoinColumn(name = "musicians_id"), inverseJoinColumns = @JoinColumn(name = "albums_id"))
    private Collection<Album> albums;

    @JsonIgnoreProperties("musicians")
    @ManyToMany
    @JoinTable(name = "musicians_tags", joinColumns = @JoinColumn(name = "musicians_id"), inverseJoinColumns = @JoinColumn(name = "tags_id"))
    private Collection<Tag> tags;

    @JsonIgnoreProperties("musicians")
    @ManyToMany
    @JoinTable(name = "musicians_users", joinColumns = @JoinColumn(name = "musicians_id"), inverseJoinColumns = @JoinColumn(name = "users_id"))
    private Collection<User> users;
}