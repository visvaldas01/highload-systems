package ru.ifmo.highloadsystems.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@Entity
@Table(name = "tag_groups")
@NoArgsConstructor
public class TagGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @JsonIgnoreProperties("tag_groups")
    @OneToMany(mappedBy = "tagGroup", cascade = CascadeType.ALL)
//    @JoinColumn(name = "tag_group_id", nullable = false)
    private Collection<Tag> tags;
}