package ru.ifmo.fileservice.model.entity;

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

    @JsonIgnoreProperties("tags")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "songs_tags", joinColumns = @JoinColumn(name = "tags_id"), inverseJoinColumns = @JoinColumn(name = "songs_id"))
    private Collection<Song> songs;

    @JsonIgnoreProperties("tags")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "albums_tags", joinColumns = @JoinColumn(name = "tags_id"), inverseJoinColumns = @JoinColumn(name = "albums_id"))
    private Collection<Album> albums;

    @JsonIgnoreProperties("tags")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "musicians_tags", joinColumns = @JoinColumn(name = "tags_id"), inverseJoinColumns = @JoinColumn(name = "musicians_id"))
    private Collection<Musician> musicians;

    @JsonIgnoreProperties("tags")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tag_group_id", referencedColumnName = "id", nullable = false)
    private TagGroup tagGroup;
}